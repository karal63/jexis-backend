package com.jexis.jexis_backend.subscription.application.useCases;

import com.jexis.jexis_backend.plan.domain.entities.Plan;
import com.jexis.jexis_backend.stripe.application.useCases.subscription.UpgradeOrDowngradeSubUseCase;
import com.jexis.jexis_backend.subscription.domain.entities.Subscription;
import com.jexis.jexis_backend.subscription.domain.exceptions.SubscriptionExistsForAccountException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HandleExistingSubscriptionUseCase {
    private final GetActiveAccountSubscriptionUseCase getActiveAccountSubscriptionUseCase;
    private final UpgradeOrDowngradeSubUseCase upgradeOrDowngradeSubUseCase;

    public void execute(Subscription activeSubscription, Plan plan) {
        if (activeSubscription.getPlan().getId().equals(plan.getId())) {
            throw new SubscriptionExistsForAccountException();
        } else {
            upgradeOrDowngradeSubUseCase.execute(activeSubscription.getStripeSubscriptionId(), plan.getDefaultPrice().getStripePriceId());
        }
    }
}
