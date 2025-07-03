package org.example.event.service.interfaces;

import org.example.event.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    User getUser(String id);

    User updateUser(String id, User updatedUser);

    List<User> getFollowers(String id);

    List<User> getFollowing(String id);

    String follow(String id, String followerId);

    List<User> getTopOrganizers();

}
