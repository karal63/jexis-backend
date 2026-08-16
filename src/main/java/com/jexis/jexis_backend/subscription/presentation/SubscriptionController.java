package com.jexis.jexis_backend.subscription.presentation;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jexis.jexis_backend.subscription.application.useCases.GetAllSubscriptionsUseCase;
import com.jexis.jexis_backend.subscription.domain.entities.Subscription;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/subscriptions")
@RequiredArgsConstructor
@PreAuthorize("@userAuthorization.isAdmin(authentication.principal.roles())")
public class SubscriptionController {
    private final GetAllSubscriptionsUseCase getAllSubscriptionsUseCase;

    @GetMapping
    public List<Subscription> list() {
        return getAllSubscriptionsUseCase.execute();
    }
}
