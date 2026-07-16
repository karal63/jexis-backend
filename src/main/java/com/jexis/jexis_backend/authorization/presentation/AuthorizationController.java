package com.jexis.jexis_backend.authorization.presentation;

import com.jexis.jexis_backend.authorization.application.dto.AuthorizationResponseDto;
import com.jexis.jexis_backend.authorization.application.useCases.GetAuthorizationUseCase;
import com.jexis.jexis_backend.authorization.application.useCases.GetAuthorizationsUseCase;
import com.jexis.jexis_backend.authorization.application.useCases.GetWalletAuthorizationsUseCase;
import com.jexis.jexis_backend.authorization.domain.entities.Authorization;
import com.jexis.jexis_backend.common.dtoHelpers.DtoHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * AuthorizationController
 * <p>
 * REST controller in the presentation layer responsible for exposing
 * authorization-related HTTP endpoints.
 * <p>
 * It handles request routing, input validation, and response mapping,
 * delegating all business logic execution to dedicated authorization use case
 * services (application layer).
 * <p>
 * This class does not contain domain logic; its role is limited to
 * orchestrating request/response flow between the client and the
 * application layer.
 * <p>
 * Author: Copilot
 */
@RestController
@RequestMapping("/")
public class AuthorizationController {

    private final GetAuthorizationUseCase getAuthorizationUseCase;
    private final GetAuthorizationsUseCase getAuthorizationsUseCase;
    private final GetWalletAuthorizationsUseCase getWalletAuthorizationsUseCase;
    private final DtoHelper dtoHelper;

    public AuthorizationController(
            GetAuthorizationUseCase getAuthorizationUseCase,
            GetAuthorizationsUseCase getAuthorizationsUseCase,
            GetWalletAuthorizationsUseCase getWalletAuthorizationsUseCase,
            DtoHelper dtoHelper) {
        this.getAuthorizationUseCase = getAuthorizationUseCase;
        this.getAuthorizationsUseCase = getAuthorizationsUseCase;
        this.getWalletAuthorizationsUseCase = getWalletAuthorizationsUseCase;
        this.dtoHelper = dtoHelper;
    }

    /**
     * Retrieves all authorizations.
     * Endpoint: GET /admin/authorizations
     *
     * @return list of all authorizations
     */
    @GetMapping("/admin/authorizations")
    @PreAuthorize("@userAuthorization.isAdmin(authentication.principal.roles())")
    public List<AuthorizationResponseDto> getAllAuthorizations() {
        return getAuthorizationsUseCase.execute().stream()
                .map(dtoHelper::toAuthorizationDto)
                .toList();
    }

    /**
     * Retrieves a single authorization by its identifier.
     * Endpoint: GET /authorizations/{id}
     *
     * @param id the unique identifier of the authorization to retrieve
     * @return the matching authorization entity
     */
    @GetMapping("/authorizations/{id}")
    @PreAuthorize("@authorizationAuthorization.canView(authentication.principal.id(), #id)")
    public AuthorizationResponseDto getAuthorization(@PathVariable UUID id) {
        Authorization authorization = getAuthorizationUseCase.execute(id);
        return dtoHelper.toAuthorizationDto(authorization);
    }

    /**
     * Retrieves all authorizations for a specific wallet.
     * Endpoint: GET /wallets/{walletId}/authorizations
     *
     * @param walletId the unique identifier of the wallet
     * @return list of authorizations for the wallet
     */
    @GetMapping("/wallets/{walletId}/authorizations")
    @PreAuthorize("@authorizationAuthorization.canViewWallet(authentication.principal.id(), #walletId)")
    public List<AuthorizationResponseDto> getWalletAuthorizations(@PathVariable UUID walletId) {
        return getWalletAuthorizationsUseCase.execute(walletId).stream()
                .map(dtoHelper::toAuthorizationDto)
                .toList();
    }
}
