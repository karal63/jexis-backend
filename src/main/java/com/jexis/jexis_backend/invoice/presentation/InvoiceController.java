package com.jexis.jexis_backend.invoice.presentation;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jexis.jexis_backend.invoice.application.useCases.GetAllInvoicesUseCase;
import com.jexis.jexis_backend.invoice.application.useCases.GetSubscriptionInvoicesUseCase;
import com.jexis.jexis_backend.invoice.domain.entities.Invoice;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/invoices")
@RequiredArgsConstructor
@PreAuthorize("@userAuthorization.isAdmin(authentication.principal.roles())")
public class InvoiceController {
    private final GetAllInvoicesUseCase getAllInvoicesUseCase;
    private final GetSubscriptionInvoicesUseCase getSubscriptionInvoicesUseCase;

    @GetMapping
    public List<Invoice> list() {
        return getAllInvoicesUseCase.execute();
    }

    @GetMapping("/subscription/{subscriptionId}")
    public List<Invoice> listBySubscription(@PathVariable UUID subscriptionId) {
        return getSubscriptionInvoicesUseCase.execute(subscriptionId);
    }
}
