package com.jexis.jexis_backend.subscription.application.useCases;

import com.jexis.jexis_backend.subscription.domain.entities.Subscription;
import com.jexis.jexis_backend.subscription.infrastructure.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetMySubscriptionsUseCase {
    private final SubscriptionRepository subscriptionRepository;

    public List<Subscription> execute(UUID userId) {
        return subscriptionRepository.findByUserId(userId);
    }
}
