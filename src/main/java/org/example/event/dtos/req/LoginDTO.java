package org.example.event.dtos.req;

import lombok.Value;

@Value
public class LoginDTO {
    String email;
    String password;
}
