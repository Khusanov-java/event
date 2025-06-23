package org.example.event.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    private String email;
    private String password;
    @Schema(description = "this field for confirming password")
    private String confirmPassword;
    private String fullName;
}
