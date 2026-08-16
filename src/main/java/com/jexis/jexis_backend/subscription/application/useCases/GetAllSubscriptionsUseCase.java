package com.jexis.jexis_backend.subscription.application.useCases;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jexis.jexis_backend.subscription.domain.entities.Subscription;
import com.jexis.jexis_backend.subscription.infrastructure.SubscriptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAllSubscriptionsUseCase {
    private final SubscriptionRepository subscriptionRepository;

    @Transactional(readOnly = true)
    public List<Subscription> execute() {
        return subscriptionRepository.findAll();
    }
}
