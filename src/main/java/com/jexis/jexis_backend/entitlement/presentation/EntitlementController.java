package com.jexis.jexis_backend.entitlement.presentation;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jexis.jexis_backend.entitlement.application.dto.CreateEntitlementDto;
import com.jexis.jexis_backend.entitlement.application.useCases.CreateEntitlementUseCase;
import com.jexis.jexis_backend.entitlement.application.useCases.GetEntitlementsUseCase;
import com.jexis.jexis_backend.entitlement.domain.entities.Entitlement;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/entitlements")
@RequiredArgsConstructor
@PreAuthorize("@userAuthorization.isAdmin(authentication.principal.roles())")
public class EntitlementController {
    private final CreateEntitlementUseCase createEntitlementUseCase;
    private final GetEntitlementsUseCase getEntitlementsUseCase;

    @PostMapping
    public Entitlement create(@Valid @RequestBody CreateEntitlementDto dto) {
        return createEntitlementUseCase.execute(dto);
    }

    @GetMapping
    public List<Entitlement> list() {
        return getEntitlementsUseCase.execute();
    }
}
