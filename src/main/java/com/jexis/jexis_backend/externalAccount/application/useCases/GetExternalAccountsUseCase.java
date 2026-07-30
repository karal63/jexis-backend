package com.jexis.jexis_backend.externalAccount.application.useCases;

import com.jexis.jexis_backend.externalAccount.domain.entities.ExternalAccount;
import com.jexis.jexis_backend.externalAccount.infrastructure.ExternalAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetExternalAccountsUseCase {
    private final ExternalAccountRepository externalAccountRepository;

    public GetExternalAccountsUseCase(ExternalAccountRepository externalAccountRepository) {
        this.externalAccountRepository = externalAccountRepository;
    }

    public List<ExternalAccount> execute() {
        return externalAccountRepository.findAll();
    }
}
