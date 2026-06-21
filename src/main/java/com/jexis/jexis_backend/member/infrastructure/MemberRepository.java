package com.jexis.jexis_backend.member.infrastructure;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jexis.jexis_backend.member.domain.entities.Member;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    public List<Member> findAllByAccountId(UUID accountId);

    public Optional<Member> findByAccountIdAndUserId(UUID accountId, UUID userId);

    public Boolean existsByUserIdAndAccountId(UUID userId, UUID accountId);
}
