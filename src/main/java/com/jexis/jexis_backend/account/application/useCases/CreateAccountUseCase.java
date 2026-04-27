package com.jexis.jexis_backend.account.application.useCases;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.account.application.dto.CreateAccountDto;
import com.jexis.jexis_backend.account.domain.entities.Account;
import com.jexis.jexis_backend.account.infrastructure.AccountRepository;

@Service
public class CreateAccountUseCase {
    private final AccountRepository repo;

    public CreateAccountUseCase(AccountRepository repo) {
        this.repo = repo;
    }

    public Account execute(CreateAccountDto body) {
        Account account = new Account(body.getName(), body.getOwnerId());
        return repo.save(account);
    }
}
