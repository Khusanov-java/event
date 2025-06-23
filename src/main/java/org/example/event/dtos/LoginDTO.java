package org.example.event.dtos;

import lombok.Value;

@Value
public class LoginDTO {
    String email;
    String password;
}
