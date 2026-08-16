package com.jexis.jexis_backend.plan.application.useCases;

import com.jexis.jexis_backend.entitlement.application.useCases.GetEntitlementUseCase;
import com.jexis.jexis_backend.entitlement.domain.entities.Entitlement;
import com.jexis.jexis_backend.entitlement.infrastructure.EntitlementRepository;
import com.jexis.jexis_backend.plan.application.dto.AddPlanEntitlementDto;
import com.jexis.jexis_backend.plan.domain.entities.Plan;
import com.jexis.jexis_backend.plan.domain.entities.PlanEntitlement;
import com.jexis.jexis_backend.plan.domain.exceptions.PlanNotFoundException;
import com.jexis.jexis_backend.plan.infrastructure.PlanEntitlementRepository;
import com.jexis.jexis_backend.plan.infrastructure.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddPlanEntitlementUseCase {
    private final PlanEntitlementRepository planEntitlementRepository;
    private final PlanRepository planRepository;
    private final EntitlementRepository entitlementRepository;
    private final GetEntitlementUseCase getEntitlementUseCase;

    @Transactional
    public PlanEntitlement execute(UUID planId, AddPlanEntitlementDto dto) {
        Plan plan = planRepository.findById(planId).orElseThrow(PlanNotFoundException::new);
        Entitlement entitlement = getEntitlementUseCase.execute(dto.entitlementId());

        PlanEntitlement planEntitlement = new PlanEntitlement(plan, entitlement, dto.value());

        return planEntitlementRepository.save(planEntitlement);
    }
}
