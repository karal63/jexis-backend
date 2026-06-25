package com.jexis.jexis_backend.member.application.security;

import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.jexis.jexis_backend.member.application.useCases.CanAccessUseCase;
import com.jexis.jexis_backend.member.application.useCases.HasRoleUseCase;
import com.jexis.jexis_backend.member.domain.entities.Member;
import com.jexis.jexis_backend.member.domain.enums.Role;
import com.jexis.jexis_backend.member.infrastructure.MemberRepository;

@Component
public class MemberAuthorization {
    private final MemberRepository repo;
    private final HasRoleUseCase hasRoleUseCase;
    private final CanAccessUseCase canAccessUseCase;

    public MemberAuthorization(MemberRepository repo, HasRoleUseCase hasRoleUseCase,
            CanAccessUseCase canAccessUseCase) {
        this.repo = repo;
        this.hasRoleUseCase = hasRoleUseCase;
        this.canAccessUseCase = canAccessUseCase;
    }

    public boolean canView(UUID userId, UUID accountId) {
        return canAccessUseCase.execute(userId, accountId);
    }

    public boolean canCreate(UUID userId, UUID accountId) {
        return hasRoleUseCase.execute(userId, accountId, Role.OWNER)
                || hasRoleUseCase.execute(userId, accountId, Role.ADMIN);
    }

    public boolean canEdit(UUID userId, UUID accountId) {
        return hasRoleUseCase.execute(userId, accountId, Role.OWNER)
                || hasRoleUseCase.execute(userId, accountId, Role.ADMIN);
    }

    public boolean canDelete(UUID userId, UUID accountId, UUID memberId) {
        return hasRoleUseCase.execute(userId, accountId, Role.OWNER)
                || hasRoleUseCase.execute(userId, accountId, Role.ADMIN)
                || userId.equals(memberId);
    }
}
