package org.example.event.service.interfaces;

import org.example.event.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    User getUser(Long id);

    User updateUser(Long id, User updatedUser);

    List<User> getFollowers(Long id);

    List<User> getFollowing(Long id);

    String follow(Long id, Long followerId);

    List<User> getTopOrganizers();

}
