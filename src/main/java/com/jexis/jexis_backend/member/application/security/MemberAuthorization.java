package com.jexis.jexis_backend.member.application.security;

import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.jexis.jexis_backend.member.application.useCases.HasRoleUseCase;
import com.jexis.jexis_backend.member.domain.entities.Member;
import com.jexis.jexis_backend.member.domain.enums.Role;
import com.jexis.jexis_backend.member.infrastructure.MemberRepository;

@Component
public class MemberAuthorization {
    private final MemberRepository repo;
    private final HasRoleUseCase hasRoleUseCase;

    public MemberAuthorization(MemberRepository repo, HasRoleUseCase hasRoleUseCase) {
        this.repo = repo;
        this.hasRoleUseCase = hasRoleUseCase;
    }

    public boolean canEdit(UUID userId, UUID memberId) {
        Member member = repo.findById(memberId).orElseThrow(() -> new AccessDeniedException("Access denied"));
        return hasRoleUseCase.execute(userId, member.getAccount().getId(), Role.ADMIN)
                || hasRoleUseCase.execute(userId, member.getAccount().getId(), Role.OWNER);
    }
}
