package com.jexis.jexis_backend.user.domain.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.jexis.jexis_backend.user.domain.enums.UserRole;

import jakarta.persistence.*;

/**
 * User entity mapped to the persistence layer.
 * <p>
 * Represents a user record stored in the database and defines
 * its persistence structure (table mapping, constraints, and identifiers).
 * <p>
 * This class is managed by JPA and is used to persist and retrieve
 * user data.
 * <p>
 * Author: Leo
 */
@Entity
@Table(name = "users")
public class User {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Getter
    @Setter
    @Column(nullable = false)
    private String firstName;

    @Getter
    @Setter
    @Column(nullable = false)
    private String lastName;

    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Getter
    @Setter
    @Column(nullable = false)
    private String password;

    @Getter
    @Setter
    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private List<UserRole> roles = new ArrayList<>();

    @Getter
    @Setter
    @Column(nullable = false)
    private Boolean isActivated = false;

    @Getter
    @Setter
    @Column()
    private String activationTokenHash;

    @Getter
    @Setter
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Getter
    @Setter
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Getter
    @Setter
    private LocalDateTime deletedAt;

    @Getter
    @Setter
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public User() {
    }

    public User(String firstName, String lastName, String email, String phoneNumber, String password, String activationTokenHash, List<UserRole> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.isActivated = false;
        this.activationTokenHash = activationTokenHash;
        this.roles = roles;
    }
}
