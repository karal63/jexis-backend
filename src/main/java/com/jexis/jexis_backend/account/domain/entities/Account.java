package com.jexis.jexis_backend.account.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.jexis.jexis_backend.user.domain.entities.User;

import jakarta.persistence.*;

/**
 * Account entity mapped to the persistence layer.
 *
 * Represents an account record stored in the database and defines
 * its persistence structure (table mapping, constraints, and identifiers).
 *
 * This class is managed by JPA and is used to persist and retrieve
 * account data.
 *
 * Author: Leo
 */
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private User owner;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    private LocalDateTime deletedAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    Account() {
    }

    public Account(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public UUID getId() {
        return id;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
