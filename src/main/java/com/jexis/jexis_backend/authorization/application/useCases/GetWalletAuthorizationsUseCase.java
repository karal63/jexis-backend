package com.jexis.jexis_backend.authorization.application.useCases;

import com.jexis.jexis_backend.authorization.domain.entities.Authorization;
import com.jexis.jexis_backend.authorization.domain.enums.AuthorizationStatus;
import com.jexis.jexis_backend.authorization.infrastructure.AuthorizationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<Authorization> execute(UUID walletId, int page, int pageSize, String search, Boolean approved, AuthorizationStatus status) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return authorizationRepository.findWalletAuthorizationsWithFilters(walletId, pageable, search, approved, status);
    }
}
