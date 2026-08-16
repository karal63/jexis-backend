package com.jexis.jexis_backend.plan.application.useCases;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jexis.jexis_backend.plan.application.dto.CreatePlanDto;
import com.jexis.jexis_backend.plan.domain.entities.Plan;
import com.jexis.jexis_backend.plan.infrastructure.PlanRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreatePlanUseCase {
    private final PlanRepository planRepository;

    @Transactional
    public Plan execute(CreatePlanDto dto) {
        Plan plan = new Plan(
                dto.getName(),
                dto.getCode(),
                dto.getDescription(),
                dto.isActive());
        return planRepository.save(plan);
    }
}
