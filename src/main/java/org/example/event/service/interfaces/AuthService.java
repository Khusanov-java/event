package org.example.event.service.interfaces;

import org.example.event.dtos.req.LoginDTO;
import org.example.event.dtos.req.RegisterDTO;
import org.example.event.dtos.res.LoginResponseDTO;
import org.example.event.entity.User;

public interface AuthService {
    LoginResponseDTO login(LoginDTO loginDTO);

    void register(RegisterDTO registerDTO);

    void sendVerificationCode(User user);

    Boolean verifyCode(String userId, String code);


}
