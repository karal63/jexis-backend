package com.jexis.jexis_backend.subscription.presentation;

import java.util.List;
import java.util.UUID;

import com.jexis.jexis_backend.auth.application.dto.AuthUser;
import com.jexis.jexis_backend.subscription.application.dto.CreateCheckoutDto;
import com.jexis.jexis_backend.subscription.application.useCases.CreateCheckoutUseCase;
import com.jexis.jexis_backend.subscription.application.useCases.GetSubscriptionUseCase;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.jexis.jexis_backend.subscription.application.useCases.GetAllSubscriptionsUseCase;
import com.jexis.jexis_backend.subscription.domain.entities.Subscription;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {
    private final GetAllSubscriptionsUseCase getAllSubscriptionsUseCase;
    private final CreateCheckoutUseCase createCheckoutUseCase;
    private final GetSubscriptionUseCase getSubscriptionUseCase;

    @GetMapping("/admin/subscriptions")
    @PreAuthorize("@userAuthorization.isAdmin(authentication.principal.roles())")
    public List<Subscription> list() {
        return getAllSubscriptionsUseCase.execute();
    }

    @GetMapping("/subscriptions/{id}")
    @PreAuthorize("@subscriptionAuthorization.canView(authentication.principal.id(), #id)")
    public Subscription get(@PathVariable UUID id) {
        return getSubscriptionUseCase.execute(id);
    }

    @PostMapping("/subscriptions/checkout")
    @PreAuthorize("@subscriptionAuthorization.canCheckout(authentication.principal.id(), #dto.accountId)")
    public String createCheckoutSession(@Valid @RequestBody CreateCheckoutDto dto, @AuthenticationPrincipal AuthUser user) {
        return createCheckoutUseCase.execute(dto, user.id());
    }
}
