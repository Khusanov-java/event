package org.example.event.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
@EqualsAndHashCode(exclude = {"followers", "following"})
@ToString(exclude = {"followers", "following"})
public class User {

    @Id
    private String id = UUID.randomUUID().toString();

    private String name;

    @Column(unique = true)
    private String email;

    private String password;
    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    private String bio;

    public enum Role {
        USER, ORGANIZER, ADMIN
    }

    @Column(nullable = false)
    private boolean verified = false;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_following", joinColumns = @JoinColumn(name = "follower_id"), inverseJoinColumns = @JoinColumn(name = "following_id"))
    private Set<User> following = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "following", fetch = FetchType.EAGER)
    private Set<User> followers = new HashSet<>();
}
