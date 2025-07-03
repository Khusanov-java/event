package org.example.event.dtos.req;

import lombok.Data;


@Data
public class LoginDTO {
    private String email;
    private String password;
    private boolean rememberMe;
}


