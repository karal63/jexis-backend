package com.jexis.jexis_backend.user.infrastructure;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jexis.jexis_backend.user.domain.entities.User;
import com.jexis.jexis_backend.user.domain.enums.UserRole;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmailAndIsDeletedFalse(String email);

    Optional<User> findByEmailOrPhoneNumber(String email, String phoneNumber);

    Optional<User> findByIdAndIsDeletedFalse(UUID id);

    Optional<User> findByEmail(String email);

    Optional<User> findByActivationTokenHash(String token);

    @Query(value = """
            SELECT *
            FROM users u
            WHERE
            (:search IS NULL
                OR lower(CAST(u.id AS text)) LIKE concat('%', lower(CAST(:search AS text)), '%')
                OR lower(u.first_name) LIKE concat('%', lower(CAST(:search AS text)), '%')
                OR lower(u.last_name) LIKE concat('%', lower(CAST(:search AS text)), '%')
                OR lower(u.email) LIKE concat('%', lower(CAST(:search AS text)), '%')
                OR lower(u.phone_number) LIKE concat('%', lower(CAST(:search AS text)), '%'))
            AND (:role IS NULL OR CAST(:role AS text) = ANY(u.roles))
            AND (:isActivated IS NULL OR u.is_activated = :isActivated)
            """, nativeQuery = true)
    Page<User> findUsersWithFilters(
            Pageable pageable,
            @Param("search") String search,
            @Param("role") UserRole role,
            @Param("isActivated") Boolean isActivated);
}