package com.jexis.jexis_backend.plan.presentation;

import java.util.List;
import java.util.UUID;

import com.jexis.jexis_backend.plan.application.dto.SavePlanEntitlementDto;
import com.jexis.jexis_backend.plan.application.dto.UpdatePlanDto;
import com.jexis.jexis_backend.plan.application.useCases.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.jexis.jexis_backend.plan.application.dto.CreatePlanDto;
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
    private final SavePlanEntitlementsUseCase savePlanEntitlementsUseCase;
    private final DeletePlanUseCase deletePlanUseCase;
    private final UpdatePlanUseCase updatePlanUseCase;

    @PostMapping
    public Plan create(@Valid @RequestBody CreatePlanDto dto) {
        return createPlanUseCase.execute(dto);
    }

    @GetMapping
    public List<Plan> list() {
        return getAllPlansUseCase.execute();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        deletePlanUseCase.execute(id);
    }

    @PatchMapping("/{id}")
    public Plan update(@PathVariable UUID id, @Valid @RequestBody UpdatePlanDto dto) {
        return updatePlanUseCase.execute(id, dto);
    }

    @GetMapping("/{id}/entitlements")
    public List<PlanEntitlement> listEntitlements(@PathVariable UUID id) {
        return getPlanEntitlementsUseCase.execute(id);
    }

    @PostMapping("/{id}/entitlements")
    public List<PlanEntitlement> savePlanEntitlements(@PathVariable UUID id, @Valid @RequestBody List<SavePlanEntitlementDto> dtos) {
        return savePlanEntitlementsUseCase.execute(id, dtos);
    }
}
