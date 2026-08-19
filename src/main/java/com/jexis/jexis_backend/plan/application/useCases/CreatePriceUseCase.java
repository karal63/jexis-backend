package com.jexis.jexis_backend.plan.application.useCases;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jexis.jexis_backend.plan.application.dto.CreatePriceDto;
import com.jexis.jexis_backend.plan.domain.entities.Plan;
import com.jexis.jexis_backend.plan.domain.entities.Price;
import com.jexis.jexis_backend.plan.infrastructure.PriceRepository;
import com.jexis.jexis_backend.stripe.application.useCases.plan.price.CreateStripePriceUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreatePriceUseCase {
    private final PriceRepository priceRepository;
    private final GetPlanUseCase getPlanUseCase;
    private final CreateStripePriceUseCase createStripePriceUseCase;

    @Transactional
    public Price execute(UUID planId, CreatePriceDto dto) {
        Plan plan = getPlanUseCase.execute(planId);
        com.stripe.model.Price stripePrice = createStripePriceUseCase.execute(plan.getStripePlanId(), dto);

        String interval = normalizeInterval(dto.getInterval());
        Integer intervalCount = dto.getIntervalCount() != null && dto.getIntervalCount() > 0 ? dto.getIntervalCount() : 1;

        Price price = new Price(
                plan,
                stripePrice.getId(),
                dto.getCurrency().toLowerCase(),
                dto.getUnitAmount(),
                interval,
                intervalCount,
                dto.isActive()
        );

        return priceRepository.save(price);
    }

    private String normalizeInterval(String interval) {
        if (interval == null) {
            throw new IllegalArgumentException("Interval is required. Supported intervals are monthly and yearly.");
        }
        String lower = interval.trim().toLowerCase();
        return switch (lower) {
            case "month", "monthly" -> "month";
            case "year", "yearly" -> "year";
            default -> throw new IllegalArgumentException("Unsupported interval: " + interval + ". Supported intervals are monthly and yearly.");
        };
    }
}
