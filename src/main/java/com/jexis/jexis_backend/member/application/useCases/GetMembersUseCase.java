package com.jexis.jexis_backend.member.application.useCases;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.member.domain.entities.Member;
import com.jexis.jexis_backend.member.infrastructure.MemberRepository;

@Service
public class GetMembersUseCase {
    private final MemberRepository repo;

    public GetMembersUseCase(MemberRepository repo) {
        this.repo = repo;
    }

    public List<Member> execute(UUID accountId) {
        List<Member> members = repo.findAllByAccountId(accountId);
        return members;
    }
}
