package com.jexis.jexis_backend.plan.application.useCases;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jexis.jexis_backend.plan.application.dto.UpdatePriceDto;
import com.jexis.jexis_backend.plan.domain.entities.Price;
import com.jexis.jexis_backend.plan.infrastructure.PriceRepository;
import com.jexis.jexis_backend.stripe.application.useCases.plan.price.UpdateStripePriceUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdatePriceUseCase {
    private final PriceRepository priceRepository;
    private final GetPriceUseCase getPriceUseCase;
    private final UpdateStripePriceUseCase updateStripePriceUseCase;

    @Transactional
    public Price execute(UUID id, UpdatePriceDto dto) {
        Price price = getPriceUseCase.execute(id);

        price.setActive(dto.isActive());

        updateStripePriceUseCase.execute(price.getStripePriceId(), dto);

        return priceRepository.save(price);
    }
}
