package com.jexis.jexis_backend.subscription.application.useCases;

import com.jexis.jexis_backend.account.application.useCases.GetAccountUseCase;
import com.jexis.jexis_backend.account.domain.entities.Account;
import com.jexis.jexis_backend.plan.application.useCases.GetPlanUseCase;
import com.jexis.jexis_backend.plan.application.useCases.GetPriceByStripeIdUseCase;
import com.jexis.jexis_backend.plan.domain.entities.Plan;
import com.jexis.jexis_backend.plan.domain.entities.Price;
import com.jexis.jexis_backend.subscription.domain.entities.Subscription;
import com.jexis.jexis_backend.subscription.domain.enums.SubscriptionStatus;
import com.jexis.jexis_backend.subscription.infrastructure.SubscriptionRepository;
import com.jexis.jexis_backend.subscription.infrastructure.stripe.SubscriptionMapper;
import com.jexis.jexis_backend.user.application.useCases.GetUserUseCase;
import com.jexis.jexis_backend.user.domain.entities.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class SyncSubscriptionUseCase {
    private final SubscriptionRepository subscriptionRepository;
    private final GetUserUseCase getUserUseCase;
    private final GetAccountUseCase getAccountUseCase;
    private final GetPlanUseCase getPlanUseCase;
    private final GetPriceByStripeIdUseCase getPriceByStripeIdUseCase;
    private final SubscriptionMapper subscriptionMapper;

    public SyncSubscriptionUseCase(SubscriptionRepository subscriptionRepository, GetUserUseCase getUserUseCase, GetAccountUseCase getAccountUseCase, GetPlanUseCase getPlanUseCase, GetPriceByStripeIdUseCase getPriceByStripeIdUseCase, SubscriptionMapper subscriptionMapper) {
        this.subscriptionRepository = subscriptionRepository;
        this.getUserUseCase = getUserUseCase;
        this.getAccountUseCase = getAccountUseCase;
        this.getPlanUseCase = getPlanUseCase;
        this.getPriceByStripeIdUseCase = getPriceByStripeIdUseCase;
        this.subscriptionMapper = subscriptionMapper;
    }

    public void execute(com.stripe.model.Subscription stripeSub) {
        Subscription subscription = subscriptionRepository
                .findByStripeSubscriptionId(stripeSub.getId())
                .orElseGet(Subscription::new);

        String userId = stripeSub.getMetadata().get("userId");
        String accountId = stripeSub.getMetadata().get("accountId");
        String planId = stripeSub.getMetadata().get("planId");

        User user = getUserUseCase.execute(UUID.fromString(userId));
        Account account = getAccountUseCase.execute(UUID.fromString(accountId));
        Plan plan = getPlanUseCase.execute(UUID.fromString(planId));

        var subscriptionItem = stripeSub
                .getItems()
                .getData()
                .get(0);

        Long currentPeriodStart = subscriptionItem.getCurrentPeriodStart();
        Long currentPeriodEnd = subscriptionItem.getCurrentPeriodEnd();

        String stripePriceId = subscriptionItem
                .getPrice()
                .getId();

        Price price = getPriceByStripeIdUseCase.execute(stripePriceId);

        LocalDateTime convertedPeriodStart = Instant
                .ofEpochSecond(currentPeriodStart)
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();

        LocalDateTime convertedPeriodEnd = Instant
                .ofEpochSecond(currentPeriodEnd)
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();

        LocalDateTime convertedCanceledAt = stripeSub.getCanceledAt() != null ? Instant
                .ofEpochSecond(stripeSub.getCanceledAt())
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime() : null;

        subscription.setUser(user);
        subscription.setAccount(account);
        subscription.setPlan(plan);
        subscription.setPrice(price);
        subscription.setStripeSubscriptionId(stripeSub.getId());
        subscription.setStatus(subscriptionMapper.mapSubscriptionStatus(stripeSub.getStatus()));
        subscription.setCurrentPeriodStart(convertedPeriodStart);
        subscription.setCurrentPeriodEnd(convertedPeriodEnd);
        subscription.setCancelAtPeriodEnd(stripeSub.getCancelAtPeriodEnd());
        subscription.setCanceledAt(convertedCanceledAt);

        subscriptionRepository.save(subscription);
    }
}
