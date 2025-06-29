package org.example.event.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import org.example.event.dtos.req.LoginDTO;
import org.example.event.dtos.req.RegisterDTO;
import org.example.event.entity.User;
import org.example.event.repo.UserRepository;
import org.example.event.service.interfaces.AuthService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    @SneakyThrows
    @Override
    public User login(LoginDTO loginDTO) {
        Optional<User> byEmail = userRepository.findByEmail(loginDTO.getEmail());
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            if (user.getPassword().equals(loginDTO.getPassword())) {
                return user;
            }
        } else {
            throw new BadRequestException("user not found");
        }
        return null;
    }

    @SneakyThrows
    @Override
    public void register(RegisterDTO registerDTO) {
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        user.setBio(registerDTO.getBio());
        user.setName(registerDTO.getFullName());
        userRepository.save(user);
    }
}
