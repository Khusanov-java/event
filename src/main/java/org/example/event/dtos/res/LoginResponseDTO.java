package org.example.event.dtos.res;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String accessToken;
    private String refreshToken;
    private String userId;
    private String email;
    private String name;
    private String role;
}
