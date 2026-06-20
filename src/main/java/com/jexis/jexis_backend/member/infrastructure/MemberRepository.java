package com.jexis.jexis_backend.member.infrastructure;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jexis.jexis_backend.member.domain.entities.Member;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    public Optional<Member> findByAccountIdAndUserId(UUID accountId, UUID userId);
}
