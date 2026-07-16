package com.jexis.jexis_backend.authorization.application.useCases;

import com.jexis.jexis_backend.authorization.domain.entities.Authorization;
import com.jexis.jexis_backend.authorization.infrastructure.AuthorizationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetWalletAuthorizationsUseCase {
    private final AuthorizationRepository authorizationRepository;

    public GetWalletAuthorizationsUseCase(AuthorizationRepository authorizationRepository) {
        this.authorizationRepository = authorizationRepository;
    }

    public List<Authorization> execute(UUID walletId) {
        return authorizationRepository.findByWalletId(walletId);
    }
}
