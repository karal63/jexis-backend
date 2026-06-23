package com.jexis.jexis_backend.member.application.useCases;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.member.domain.entities.Member;
import com.jexis.jexis_backend.member.infrastructure.MemberRepository;

@Service
public class GetAccountMembersUseCase {
    private final MemberRepository memberRepo;

    public GetAccountMembersUseCase(MemberRepository memberRepo) {
        this.memberRepo = memberRepo;
    }

    public List<Member> execute(UUID accountId) {
        return memberRepo.findAllByAccountId(accountId);
    }
}
