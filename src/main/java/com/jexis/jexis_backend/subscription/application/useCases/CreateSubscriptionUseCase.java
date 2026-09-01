package com.jexis.jexis_backend.subscription.application.useCases;

import com.jexis.jexis_backend.plan.application.useCases.GetPlanUseCase;
import com.jexis.jexis_backend.plan.domain.entities.Plan;
import com.jexis.jexis_backend.stripe.application.useCases.subscription.CreateStripeSubscriptionUseCase;
import com.jexis.jexis_backend.subscription.application.dto.CreateSubscriptionDto;
import com.jexis.jexis_backend.subscription.domain.exceptions.ForbiddenException;
import com.jexis.jexis_backend.user.application.useCases.GetPaymentMethodUseCase;
import com.jexis.jexis_backend.user.application.useCases.GetUserUseCase;
import com.jexis.jexis_backend.user.domain.entities.User;
import com.stripe.model.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateSubscriptionUseCase {
    private final CreateStripeSubscriptionUseCase createStripeSubscriptionUseCase;
    private final GetUserUseCase getUserUseCase;
    private final GetPlanUseCase getPlanUseCase;
    private final GetPaymentMethodUseCase getPaymentMethodUseCase;

    public void execute(CreateSubscriptionDto dto, UUID userId) {
        User user = getUserUseCase.execute(userId);
        Plan plan = getPlanUseCase.execute(dto.getPlanId());

        PaymentMethod paymentMethod = getPaymentMethodUseCase.execute(user.getStripeCustomerId(), dto.getPaymentMethodId());
        if (paymentMethod.getCustomer().equals(user.getStripeCustomerId())) {
            createStripeSubscriptionUseCase.execute(
                    user.getStripeCustomerId(),
                    dto.getPaymentMethodId(),
                    plan.getDefaultPrice().getStripePriceId(),
                    user.getId(),
                    dto.getAccountId(),
                    dto.getPlanId()
            );
        } else {
            throw new ForbiddenException();
        }
    }
}
