package com.jexis.jexis_backend.member.application.useCases;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.account.application.useCases.GetAccountUseCase;
import com.jexis.jexis_backend.account.domain.entities.Account;
import com.jexis.jexis_backend.member.application.dto.CreateMemberDto;
import com.jexis.jexis_backend.member.domain.entities.Member;
import com.jexis.jexis_backend.member.domain.exceptions.MemberExistsException;
import com.jexis.jexis_backend.member.infrastructure.MemberRepository;
import com.jexis.jexis_backend.user.application.useCases.GetUserUseCase;
import com.jexis.jexis_backend.user.domain.entities.User;

@Service
public class AddMemberUseCase {
    private final MemberRepository repo;
    private final GetAccountUseCase getAccountUseCase;
    private final GetUserUseCase getUserUseCase;

    public AddMemberUseCase(MemberRepository repo, GetAccountUseCase getAccountUseCase, GetUserUseCase getUserUseCase) {
        this.repo = repo;
        this.getAccountUseCase = getAccountUseCase;
        this.getUserUseCase = getUserUseCase;
    }

    public Member execute(CreateMemberDto body) {
        repo.findByAccountIdAndUserId(body.accountId(), body.userId()).ifPresent(member -> {
            throw new MemberExistsException();
        });

        Account account = getAccountUseCase.execute(body.accountId());
        User user = getUserUseCase.execute(body.userId());

        Member member = new Member(account, user, body.role());
        Member savedMember = repo.save(member);
        return savedMember;

    }
}
