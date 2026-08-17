package com.jexis.jexis_backend.plan.application.useCases;

import com.jexis.jexis_backend.plan.application.dto.UpdatePlanDto;
import com.jexis.jexis_backend.plan.domain.entities.Plan;
import com.jexis.jexis_backend.plan.infrastructure.PlanRepository;
import com.jexis.jexis_backend.stripe.application.useCases.plan.product.UpdateStripeProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdatePlanUseCase {
    private final PlanRepository planRepository;
    private final GetPlanUseCase getPlanUseCase;
    private final UpdateStripeProductUseCase updateStripeProductUseCase;

    @Transactional
    public Plan execute(UUID id, UpdatePlanDto dto) {
        Plan plan = getPlanUseCase.execute(id);

        plan.setName(dto.getName());
        plan.setDescription(dto.getDescription());
        plan.setCode(dto.getCode());

        updateStripeProductUseCase.execute(plan.getStripePlanId(), dto);

        return planRepository.save(plan);
    }
}
