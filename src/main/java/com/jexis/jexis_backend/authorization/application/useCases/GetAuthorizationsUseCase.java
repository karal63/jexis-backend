package com.jexis.jexis_backend.authorization.application.useCases;

import com.jexis.jexis_backend.authorization.domain.entities.Authorization;
import com.jexis.jexis_backend.authorization.domain.enums.AuthorizationStatus;
import com.jexis.jexis_backend.authorization.infrastructure.AuthorizationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAuthorizationsUseCase {
    private final AuthorizationRepository authorizationRepository;

    public GetAuthorizationsUseCase(AuthorizationRepository authorizationRepository) {
        this.authorizationRepository = authorizationRepository;
    }

    public List<Authorization> execute() {
        return authorizationRepository.findAll();
    }

    public Page<Authorization> execute(int page, int pageSize, String search, Boolean approved, AuthorizationStatus status) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return authorizationRepository.findAuthorizationsWithFilters(pageable, search, approved, status);
    }
}
