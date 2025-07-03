package org.example.event.repo;

import org.example.event.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    List<User> findTop5ByRoleOrderByIdDesc(User.Role role);

}