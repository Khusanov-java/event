package org.example.event.service.interfaces;

import org.example.event.dtos.LoginDTO;
import org.example.event.dtos.RegisterDTO;
import org.example.event.entity.User;

public interface AuthService {
    User login(LoginDTO loginDTO);

    void register(RegisterDTO registerDTO);
}
