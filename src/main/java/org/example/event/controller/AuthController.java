package org.example.event.controller;

import lombok.RequiredArgsConstructor;
import org.example.event.dtos.req.LoginDTO;
import org.example.event.dtos.req.RegisterDTO;
import org.example.event.entity.User;
import org.example.event.service.interfaces.AuthService;
import org.example.event.utils.Util;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Util.AUTH_PATH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public HttpEntity<?> login(@RequestBody LoginDTO loginDTO) {
        User loggedUser = authService.login(loginDTO);
        return ResponseEntity.ok(loggedUser);
    }

    @PostMapping("/register")
    public HttpEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        authService.register(registerDTO);
        return ResponseEntity.ok().build();
    }
}
