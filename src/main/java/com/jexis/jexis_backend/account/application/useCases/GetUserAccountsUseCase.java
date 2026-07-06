package com.jexis.jexis_backend.account.application.useCases;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.account.domain.entities.Account;
import com.jexis.jexis_backend.account.infrastructure.AccountRepository;

@Service
public class GetUserAccountsUseCase {
    AccountRepository repo;

    public GetUserAccountsUseCase(AccountRepository repo) {
        this.repo = repo;
    }

    public List<Account> execute(UUID id) {
        return repo.findAllByOwnerIdAndIsDeletedFalse(id);
    }
}
