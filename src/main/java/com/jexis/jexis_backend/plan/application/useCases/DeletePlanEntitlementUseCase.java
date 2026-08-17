package com.jexis.jexis_backend.plan.application.useCases;

import com.jexis.jexis_backend.plan.infrastructure.PlanEntitlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeletePlanEntitlementUseCase {
    private final PlanEntitlementRepository planEntitlementRepository;

    public void execute(UUID planEntitlementId) {
        planEntitlementRepository.deleteById(planEntitlementId);
    }
}
