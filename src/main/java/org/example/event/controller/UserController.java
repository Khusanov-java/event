package org.example.event.controller;

import lombok.RequiredArgsConstructor;
import org.example.event.entity.User;
import org.example.event.repo.UserRepository;
import org.example.event.utils.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(Util.USER_PATH)
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserProfile(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (updatedUser.getName() != null) {
            user.setName(updatedUser.getName());
        }

        if (updatedUser.getEmail() != null) {
            user.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getProfileImage() != null) {
            user.setProfileImage(updatedUser.getProfileImage());
        }

        if (updatedUser.getBio() != null) {
            user.setBio(updatedUser.getBio());
        }

        return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping("/{id}/followers")
    public ResponseEntity<List<User>> getFollowers(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(new  ArrayList<>(user.getFollowers()));
    }

    @GetMapping("/{id}/following")
    public ResponseEntity<List<User>> getFollowing(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(new ArrayList<>(user.getFollowing()));
    }

    @Transactional
    @PostMapping("/{id}/follow")
    public ResponseEntity<String> followUser(@PathVariable Long id, @RequestParam Long followerId) {
        User targetUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Follower not found"));

            targetUser.getFollowers().add(follower);
        follower.getFollowing().add(targetUser);
        userRepository.save(targetUser);
        userRepository.save(follower);

        return ResponseEntity.ok("Now following user with id: " + id);
    }







}
