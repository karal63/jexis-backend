package com.jexis.jexis_backend.subscription.application.useCases;

import com.jexis.jexis_backend.account.application.useCases.GetAccountUseCase;
import com.jexis.jexis_backend.account.domain.entities.Account;
import com.jexis.jexis_backend.plan.application.useCases.GetPlanUseCase;
import com.jexis.jexis_backend.plan.application.useCases.GetPriceByStripeIdUseCase;
import com.jexis.jexis_backend.plan.domain.entities.Plan;
import com.jexis.jexis_backend.plan.domain.entities.Price;
import com.jexis.jexis_backend.subscription.domain.entities.Subscription;
import com.jexis.jexis_backend.subscription.infrastructure.SubscriptionRepository;
import com.jexis.jexis_backend.user.application.useCases.GetUserUseCase;
import com.jexis.jexis_backend.user.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateSubscriptionUseCase {
    private final GetUserUseCase getUserUseCase;
    private final GetAccountUseCase getAccountUseCase;
    private final GetPriceByStripeIdUseCase getPriceByStripeIdUseCase;
    private final SubscriptionRepository subscriptionRepository;
    private final GetPlanUseCase getPlanUseCase;

    public Subscription execute(com.stripe.model.Subscription stripeSubscription) {
        try {
            String userId = stripeSubscription.getMetadata().get("userId");
            String accountId = stripeSubscription.getMetadata().get("accountId");
            String planId = stripeSubscription.getMetadata().get("planId");

            User user = getUserUseCase.execute(UUID.fromString(userId));
            Account account = getAccountUseCase.execute(UUID.fromString(accountId));
            Plan plan = getPlanUseCase.execute(UUID.fromString(planId));

            var subscriptionItem = stripeSubscription
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

            Subscription newSubscription = new Subscription(
                    user,
                    account,
                    plan,
                    price,
                    stripeSubscription.getId(),
                    stripeSubscription.getStatus(),
                    convertedPeriodStart,
                    convertedPeriodEnd,
                    stripeSubscription.getCancelAtPeriodEnd()
            );

            return subscriptionRepository.save(newSubscription);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
