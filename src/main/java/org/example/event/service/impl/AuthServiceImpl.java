package org.example.event.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.event.dtos.req.LoginDTO;
import org.example.event.dtos.req.RegisterDTO;
import org.example.event.dtos.res.LoginResponseDTO;
import org.example.event.entity.User;
import org.example.event.repo.UserRepository;
import org.example.event.service.interfaces.AuthService;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.BadRequestException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final Keycloak keycloak;

    private final Map<String, String> codeMap = new ConcurrentHashMap<>();

    @SneakyThrows
    @Override
    public LoginResponseDTO login(LoginDTO loginDTO) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/realms/event/protocol/openid-connect/token"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString("grant_type=password" + "&client_id=event-client" + "&client_secret=EH1AKL6mSJuEKINFqa4v3sCJglXLpxfk"
                            + "&username=" + URLEncoder.encode(loginDTO.getEmail(), StandardCharsets.UTF_8)
                            + "&password=" + URLEncoder.encode(loginDTO.getPassword(), StandardCharsets.UTF_8))).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.body());

            if (jsonNode.has("error")) {
                String errorMessage = jsonNode.has("error_description") ? jsonNode.get("error_description").asText() : jsonNode.get("error").asText();
                throw new BadRequestException("Login failed: " + errorMessage);
            }

            String accessToken = jsonNode.get("access_token").asText();
            String refreshToken = jsonNode.get("refresh_token").asText();

            User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() -> new BadRequestException("User not found"));

            return new LoginResponseDTO(accessToken, refreshToken, user.getId(), user.getEmail(), user.getName(), user.getRole().name());
        } catch (IOException e) {
            throw new BadRequestException("Failed to connect to authentication server: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new BadRequestException("Authentication process was interrupted");
        }
    }


    @Transactional
    @SneakyThrows
    @Override
    public void register(RegisterDTO dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        String userId = createKeycloakUser(dto);

        User user = User.builder().id(userId)
                .email(dto.getEmail())
                .password(dto.getPassword()).name(dto.getFullName())
                .bio(dto.getBio())
                .role(User.Role.USER).build();

        sendVerificationCode(user);

        userRepository.save(user);
    }

    private String createKeycloakUser(RegisterDTO dto) {
        try {
            String token = getAdminAccessToken();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/admin/realms/event/users"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .POST(HttpRequest.BodyPublishers.ofString(generateUserJson(dto)))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {

                String location = response.headers().firstValue("Location").orElse("");
                return location.replaceAll(".*/([^/]+)$", "$1");
            } else {
                throw new RuntimeException("User yaratilmadi: " + response.statusCode() + " | " + response.body());
            }
        } catch (Exception e) {
            throw new RuntimeException("Foydalanuvchi yaratishda xatolik", e);
        }
    }

    public String getAdminAccessToken() throws IOException, InterruptedException {
        String url = "http://localhost:8081/realms/master/protocol/openid-connect/token";

        String clientId = "admin-cli";
        String username = "admin";
        String password = "admin";

        String body = "grant_type=password"
                + "&client_id=" + clientId
                + "&username=" + URLEncoder.encode(username, StandardCharsets.UTF_8)
                + "&password=" + URLEncoder.encode(password, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.body());

        if (jsonNode.has("access_token")) {
            return jsonNode.get("access_token").asText();
        } else {
            throw new RuntimeException("Token olishda xatolik: " + response.body());
        }
    }

    private String generateUserJson(RegisterDTO dto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> user = new HashMap<>();
        user.put("username", dto.getEmail());
        user.put("email", dto.getEmail());
        user.put("firstName", dto.getFullName());
        user.put("enabled", true);
        user.put("emailVerified", false);

        Map<String, String> credentials = new HashMap<>();
        credentials.put("type", "password");
        credentials.put("value", dto.getPassword());
        credentials.put("temporary", "false");

        user.put("credentials", List.of(credentials));

        return mapper.writeValueAsString(user);
    }


    public void sendVerificationCode(User user) {
        String code = String.format("%06d", new Random().nextInt(999999));


        codeMap.put(user.getId(), code);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Tasdiqlash kodingiz");
        message.setText("Sizning email tasdiqlash kodingiz: " + code);
        mailSender.send(message);
    }

    public Boolean verifyCode(String userId, String code) {
        String savedCode = codeMap.get(userId);
        if (savedCode != null && savedCode.equals(code)) {

            User user = userRepository.findById(userId).orElseThrow();
            RealmResource realm = keycloak.realm("event");
            UsersResource users = realm.users();

            UserRepresentation kcUser = users.get(user.getId()).toRepresentation();
            kcUser.setEmailVerified(true);
            users.get(user.getId()).update(kcUser);


            user.setVerified(true);
            userRepository.save(user);

            codeMap.remove(userId);
            return true;
        }
        return false;
    }

}