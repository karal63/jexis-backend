package com.jexis.jexis_backend.subscription.presentation;

import java.util.List;

import com.jexis.jexis_backend.auth.application.dto.AuthUser;
import com.jexis.jexis_backend.subscription.application.dto.CreateCheckoutDto;
import com.jexis.jexis_backend.subscription.application.useCases.CreateCheckoutUseCase;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.jexis.jexis_backend.subscription.application.useCases.GetAllSubscriptionsUseCase;
import com.jexis.jexis_backend.subscription.domain.entities.Subscription;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/subscriptions")
@RequiredArgsConstructor
@PreAuthorize("@userAuthorization.isAdmin(authentication.principal.roles())")
public class SubscriptionController {
    private final GetAllSubscriptionsUseCase getAllSubscriptionsUseCase;
    private final CreateCheckoutUseCase createCheckoutUseCase;

    @GetMapping
    public List<Subscription> list() {
        return getAllSubscriptionsUseCase.execute();
    }

    @PostMapping("/checkout")
    public String createCheckoutSession(@Valid @RequestBody CreateCheckoutDto dto, @AuthenticationPrincipal AuthUser user) {
        return createCheckoutUseCase.execute(dto, user.id());
    }
}
