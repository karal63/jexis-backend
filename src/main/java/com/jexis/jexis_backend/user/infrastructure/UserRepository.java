package com.jexis.jexis_backend.user.infrastructure;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jexis.jexis_backend.user.domain.entities.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);
}