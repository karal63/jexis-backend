package com.jexis.jexis_backend.plan.presentation;

import java.util.List;
import java.util.UUID;

import com.jexis.jexis_backend.plan.application.dto.AddPlanEntitlementDto;
import com.jexis.jexis_backend.plan.application.useCases.AddPlanEntitlementUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jexis.jexis_backend.plan.application.dto.CreatePlanDto;
import com.jexis.jexis_backend.plan.application.useCases.CreatePlanUseCase;
import com.jexis.jexis_backend.plan.application.useCases.GetAllPlansUseCase;
import com.jexis.jexis_backend.plan.application.useCases.GetPlanEntitlementsUseCase;
import com.jexis.jexis_backend.plan.domain.entities.Plan;
import com.jexis.jexis_backend.plan.domain.entities.PlanEntitlement;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/plans")
@RequiredArgsConstructor
@PreAuthorize("@userAuthorization.isAdmin(authentication.principal.roles())")
public class PlanController {
    private final CreatePlanUseCase createPlanUseCase;
    private final GetAllPlansUseCase getAllPlansUseCase;
    private final GetPlanEntitlementsUseCase getPlanEntitlementsUseCase;
    private final AddPlanEntitlementUseCase addPlanEntitlementUseCase;

    @PostMapping
    public Plan create(@Valid @RequestBody CreatePlanDto dto) {
        return createPlanUseCase.execute(dto);
    }

    @GetMapping
    public List<Plan> list() {
        return getAllPlansUseCase.execute();
    }

    @GetMapping("/{id}/entitlements")
    public List<PlanEntitlement> listEntitlements(@PathVariable UUID id) {
        return getPlanEntitlementsUseCase.execute(id);
    }

    @PostMapping("/{id}/entitlements")
    public PlanEntitlement addPlanEntitlement(@PathVariable UUID id, @Valid @RequestBody AddPlanEntitlementDto dto) {
        return addPlanEntitlementUseCase.execute(id, dto);
    }
}
