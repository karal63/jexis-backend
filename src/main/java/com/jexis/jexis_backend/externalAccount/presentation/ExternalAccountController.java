package com.jexis.jexis_backend.externalAccount.presentation;

import com.jexis.jexis_backend.common.dtoHelpers.DtoHelper;
import com.jexis.jexis_backend.externalAccount.application.dto.ExternalAccountResponseDto;
import com.jexis.jexis_backend.externalAccount.application.useCases.GetAccountExternalAccountsUseCase;
import com.jexis.jexis_backend.externalAccount.application.useCases.GetExternalAccountUseCase;
import com.jexis.jexis_backend.externalAccount.application.useCases.GetExternalAccountsUseCase;
import com.jexis.jexis_backend.externalAccount.domain.entities.ExternalAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ExternalAccountController {
    private final GetExternalAccountsUseCase getExternalAccountsUseCase;
    private final GetAccountExternalAccountsUseCase getAccountExternalAccountsUseCase;
    private final GetExternalAccountUseCase getExternalAccountUseCase;
    private final DtoHelper dtoHelper;

    @GetMapping("/admin/external-accounts")
    @PreAuthorize("@externalAccountAuthorization.canViewAll(authentication.principal.id())")
    public List<ExternalAccountResponseDto> getAllExternalAccounts() {
        return getExternalAccountsUseCase.execute().stream()
                .map(dtoHelper::toExternalAccountDto)
                .toList();
    }

    @GetMapping("/accounts/{accountId}/external-accounts")
    @PreAuthorize("@externalAccountAuthorization.canViewAccountExternalAccounts(authentication.principal.id(), #accountId)")
    public List<ExternalAccountResponseDto> getAccountExternalAccounts(@PathVariable UUID accountId) {
        return getAccountExternalAccountsUseCase.execute(accountId).stream()
                .map(dtoHelper::toExternalAccountDto)
                .toList();
    }

    @GetMapping("/external-accounts/{id}")
    @PreAuthorize("@externalAccountAuthorization.canView(authentication.principal.id(), #id)")
    public ExternalAccountResponseDto getExternalAccount(@PathVariable UUID id) {
        ExternalAccount externalAccount = getExternalAccountUseCase.execute(id);
        return dtoHelper.toExternalAccountDto(externalAccount);
    }
}

