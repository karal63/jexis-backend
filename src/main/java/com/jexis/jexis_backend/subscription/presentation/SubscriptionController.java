package com.jexis.jexis_backend.subscription.presentation;

import java.util.List;
import java.util.UUID;

import com.jexis.jexis_backend.auth.application.dto.AuthUser;
import com.jexis.jexis_backend.common.dtoHelpers.DtoHelper;
import com.jexis.jexis_backend.subscription.application.dto.SetSubscriptionPaymentMethodDto;
import com.jexis.jexis_backend.subscription.application.dto.SubscriptionResponseDto;
import com.jexis.jexis_backend.subscription.application.dto.CreateCheckoutDto;
import com.jexis.jexis_backend.subscription.application.dto.CreateSubscriptionDto;
import com.jexis.jexis_backend.subscription.application.dto.SaveSubscriptionEntitlementDto;
import com.jexis.jexis_backend.subscription.application.useCases.*;
import com.jexis.jexis_backend.subscription.domain.entities.SubscriptionEntitlement;
import com.jexis.jexis_backend.user.application.useCases.GetPaymentMethodUseCase;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {
    private final GetAllSubscriptionsUseCase getAllSubscriptionsUseCase;
    private final CreateCheckoutUseCase createCheckoutUseCase;
    private final GetSubscriptionUseCase getSubscriptionUseCase;
    private final CreateSubscriptionUseCase createSubscriptionUseCase;
    private final SetSubscriptionPaymentMethodUseCase setSubscriptionPaymentMethodUseCase;
    private final ScheduleSubscriptionCancellationUseCase scheduleSubscriptionCancellationUseCase;
    private final GetSubscriptionEntitlementsUseCase getSubscriptionEntitlementsUseCase;
    private final SaveSubscriptionEntitlementsUseCase saveSubscriptionEntitlementsUseCase;
    private final GetPaymentMethodUseCase getPaymentMethodUseCase;
    private final DtoHelper dtoHelper;

    @GetMapping("/admin/subscriptions")
    @PreAuthorize("@userAuthorization.isAdmin(authentication.principal.roles())")
    public List<SubscriptionResponseDto> list() {
        return getAllSubscriptionsUseCase.execute().stream().map(dtoHelper::toSubscriptionDto).toList();
    }

    @GetMapping("/admin/subscriptions/{id}/entitlements")
    @PreAuthorize("@userAuthorization.isAdmin(authentication.principal.roles())")
    public List<SubscriptionEntitlement> listEntitlements(@PathVariable UUID id) {
        return getSubscriptionEntitlementsUseCase.execute(id);
    }

    @PostMapping("/admin/subscriptions/{id}/entitlements")
    @PreAuthorize("@userAuthorization.isAdmin(authentication.principal.roles())")
    public List<SubscriptionEntitlement> saveEntitlements(@PathVariable UUID id, @Valid @RequestBody List<SaveSubscriptionEntitlementDto> dtos) {
        return saveSubscriptionEntitlementsUseCase.execute(id, dtos);
    }

    @GetMapping("/subscriptions/{id}")
    @PreAuthorize("@subscriptionAuthorization.canView(authentication.principal.id(), #id)")
    public SubscriptionResponseDto get(@PathVariable UUID id) {
        return dtoHelper.toSubscriptionDto(getSubscriptionUseCase.execute(id));
    }

    @PostMapping("/subscriptions/checkout")
    @PreAuthorize("@subscriptionAuthorization.canCheckout(authentication.principal.id(), #dto.accountId)")
    public String createCheckoutSession(@Valid @RequestBody CreateCheckoutDto dto, @AuthenticationPrincipal AuthUser user) {
        return createCheckoutUseCase.execute(dto, user.id());
    }

    @PostMapping("/subscriptions/create")
    @PreAuthorize("@subscriptionAuthorization.canCreate(authentication.principal.id(), #dto.accountId)")
    public void create(@Valid @RequestBody CreateSubscriptionDto dto, @AuthenticationPrincipal AuthUser user) {
        createSubscriptionUseCase.execute(dto, user.id());
    }

    @PostMapping("/subscriptions/{id}/payment-method")
    @PreAuthorize("@subscriptionAuthorization.canUpdate(authentication.principal.id(), #id)")
    public void changePaymentMethod(@PathVariable UUID id, @Valid @RequestBody SetSubscriptionPaymentMethodDto dto, @AuthenticationPrincipal AuthUser user) {
        setSubscriptionPaymentMethodUseCase.execute(id, dto, user.id());
    }

    @PostMapping("/subscriptions/{id}/cancel")
    @PreAuthorize("@subscriptionAuthorization.canUpdate(authentication.principal.id(), #id)")
    public void cancel(@PathVariable UUID id) {
        scheduleSubscriptionCancellationUseCase.execute(id);
    }
}
