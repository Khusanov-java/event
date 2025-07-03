package org.example.event.controller;

import lombok.RequiredArgsConstructor;
import org.example.event.entity.User;
import org.example.event.service.interfaces.UserService;
import org.example.event.utils.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Util.USER_PATH)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserProfile(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        return ResponseEntity.ok(userService.updateUser(id, updatedUser));
    }

    @GetMapping("/{id}/followers")
    public ResponseEntity<List<User>> getFollowers(@PathVariable String id) {
        return ResponseEntity.ok(userService.getFollowers(id));
    }

    @GetMapping("/{id}/following")
    public ResponseEntity<List<User>> getFollowing(@PathVariable String id) {
        return ResponseEntity.ok(userService.getFollowing(id));
    }

    @PostMapping("/{id}/follow")
    public ResponseEntity<String> followUser(@PathVariable String id, @RequestParam String followerId) {
        return ResponseEntity.ok(userService.follow(id, followerId));
    }

}
