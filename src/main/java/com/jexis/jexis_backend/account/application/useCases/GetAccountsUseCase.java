package com.jexis.jexis_backend.account.application.useCases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.account.domain.entities.Account;
import com.jexis.jexis_backend.account.infrastructure.AccountRepository;

/**
 * GetAccountsUseCase
 *
 * This service class implements the use case for retrieving all existing
 * accounts. It contains only the business logic related to fetching accounts,
 * such as interacting with the repository to fetch the data.
 *
 * Author: Leo
 */
@Service
public class GetAccountsUseCase {
    AccountRepository repo;

    public GetAccountsUseCase(AccountRepository repo) {
        this.repo = repo;
    }

    /**
     * Handles fetching all accounts.
     *
     * Calls the repository to fetch all accounts and returns the list of
     * accounts.
     *
     * @return list of all accounts
     */
    public List<Account> execute() {
        return repo.findAll();
    }
}
