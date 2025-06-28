package org.example.event.service.interfaces;

import org.example.event.dtos.req.LoginDTO;
import org.example.event.dtos.req.RegisterDTO;
import org.example.event.entity.User;

public interface AuthService {
    User login(LoginDTO loginDTO);

    void register(RegisterDTO registerDTO);
}
