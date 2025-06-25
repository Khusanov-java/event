package org.example.event.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.event.entity.User;
import org.example.event.repo.UserRepository;
import org.example.event.service.interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        return ResponseEntity.ok(user).getBody();
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
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
        return userRepository.save(user);
    }

    @Override
    public List<User> getFollowers(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new ArrayList<>(user.getFollowers());
    }

    @Override
    public List<User> getFollowing(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return  new ArrayList<>(user.getFollowing());
    }

    @Transactional
    @Override
    public String follow(Long id, Long followerId) {
        User targetUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Follower not found"));

        targetUser.getFollowers().add(follower);
        follower.getFollowing().add(targetUser);
        userRepository.save(targetUser);
        userRepository.save(follower);
        return "Now following user with id: " + id;
    }

    @Override
    public List<User> getTopOrganizers() {
        return userRepository.findTop5ByRoleOrderByIdDesc(User.Role.ORGANIZER);
    }


}
