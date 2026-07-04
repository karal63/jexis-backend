package com.jexis.jexis_backend.user.application.security;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.jexis.jexis_backend.user.domain.enums.UserRole;

@Component
public class UserAuthorization {

    public boolean canEdit(UUID requestingUserId, UUID targetingUserId) {
        return requestingUserId.equals(targetingUserId);
    }

    public boolean canDelete(UUID requestingUserId, UUID targetingUserId) {
        return requestingUserId.equals(targetingUserId);
    }

    public boolean isAdmin(List<UserRole> roles) {
        return roles.contains(UserRole.ADMIN);
    }
}
