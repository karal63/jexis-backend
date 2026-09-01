package com.jexis.jexis_backend.subscription.application.useCases;

import com.jexis.jexis_backend.account.application.useCases.GetAccountUseCase;
import com.jexis.jexis_backend.account.domain.entities.Account;
import com.jexis.jexis_backend.plan.application.useCases.GetPlanUseCase;
import com.jexis.jexis_backend.plan.domain.entities.Plan;
import com.jexis.jexis_backend.stripe.application.useCases.CreateStripeCustomerUseCase;
import com.jexis.jexis_backend.stripe.application.useCases.subscription.CreateStripeCheckoutUseCase;
import com.jexis.jexis_backend.subscription.application.dto.CreateCheckoutDto;
import com.jexis.jexis_backend.user.application.useCases.GetUserUseCase;
import com.jexis.jexis_backend.user.domain.entities.User;
import com.jexis.jexis_backend.user.infrastructure.UserRepository;
import com.stripe.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateCheckoutUseCase {
    private final CreateStripeCheckoutUseCase createStripeCheckoutUseCase;
    private final GetPlanUseCase getPlanUseCase;
    private final GetAccountUseCase getAccountUseCase;
    private final CreateStripeCustomerUseCase createStripeCustomerUseCase;
    private final GetUserUseCase getUserUseCase;
    private final UserRepository userRepository;

    public String execute(CreateCheckoutDto dto, UUID userId) {
        Plan plan = getPlanUseCase.execute(dto.getPlanId());
        Account account = getAccountUseCase.execute(dto.getAccountId());
        User user = getUserUseCase.execute(userId);

        if (user.getStripeCustomerId() == null) {
            Customer customer = createStripeCustomerUseCase.execute(user);
            user.setStripeCustomerId(customer.getId());
            userRepository.save(user);
        }

        return createStripeCheckoutUseCase.execute(
                plan.getId(),
                plan.getDefaultPrice().getStripePriceId(),
                userId,
                account.getId(),
                user.getStripeCustomerId()
        );
    }
}
