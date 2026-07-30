package com.jexis.jexis_backend.externalAccount.application.useCases;

import com.jexis.jexis_backend.externalAccount.domain.entities.ExternalAccount;
import com.jexis.jexis_backend.externalAccount.infrastructure.ExternalAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GetAccountExternalAccountsUseCase {
    private final ExternalAccountRepository externalAccountRepository;

    public GetAccountExternalAccountsUseCase(ExternalAccountRepository externalAccountRepository) {
        this.externalAccountRepository = externalAccountRepository;
    }

    public List<ExternalAccount> execute(UUID accountId) {
        return externalAccountRepository.findAll().stream()
                .filter(ea -> ea.getAccount().getId().equals(accountId) && !ea.isDeleted())
                .collect(Collectors.toList());
    }
}
