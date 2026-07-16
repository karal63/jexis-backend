package com.jexis.jexis_backend.authorization.application.useCases;

import com.jexis.jexis_backend.authorization.domain.entities.Authorization;
import com.jexis.jexis_backend.authorization.infrastructure.AuthorizationRepository;
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
}
