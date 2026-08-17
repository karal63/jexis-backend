package com.jexis.jexis_backend.plan.application.useCases;

import com.jexis.jexis_backend.plan.application.dto.UpdatePlanEntitlementDto;
import com.jexis.jexis_backend.plan.domain.entities.PlanEntitlement;
import com.jexis.jexis_backend.plan.infrastructure.PlanEntitlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdatePlanEntitlementUseCase {
    private final PlanEntitlementRepository planEntitlementRepository;
    private final GetPlanEntitlementUseCase getPlanEntitlementUseCase;

    public PlanEntitlement execute(UUID planEntitlementId, UpdatePlanEntitlementDto dto) {
        PlanEntitlement planEntitlement = getPlanEntitlementUseCase.execute(planEntitlementId);

        planEntitlement.setValue(dto.getValue());

        return planEntitlementRepository.save(planEntitlement);
    }
}
