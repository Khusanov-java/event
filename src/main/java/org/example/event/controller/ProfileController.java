package org.example.event.controller;

import lombok.RequiredArgsConstructor;
import org.example.event.entity.User;
import org.example.event.repo.UserRepository;
import org.example.event.utils.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Util.PROFILE_PATH)
@RequiredArgsConstructor
public class ProfileController {

    private final UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserProfile(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        return ResponseEntity.ok(user);
    }

}
