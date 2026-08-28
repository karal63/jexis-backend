package com.jexis.jexis_backend.invoice.presentation;

import java.util.List;
import java.util.UUID;

import com.jexis.jexis_backend.invoice.application.useCases.PayInvoiceUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.jexis.jexis_backend.invoice.application.useCases.GetAllInvoicesUseCase;
import com.jexis.jexis_backend.invoice.application.useCases.GetSubscriptionInvoicesUseCase;
import com.jexis.jexis_backend.invoice.domain.entities.Invoice;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class InvoiceController {
    private final GetAllInvoicesUseCase getAllInvoicesUseCase;
    private final GetSubscriptionInvoicesUseCase getSubscriptionInvoicesUseCase;
    private final PayInvoiceUseCase payInvoiceUseCase;

    @GetMapping("/admin/invoices")
    @PreAuthorize("@userAuthorization.isAdmin(authentication.principal.roles())")
    public List<Invoice> list() {
        return getAllInvoicesUseCase.execute();
    }

    @GetMapping("/invoices/subscription/{subscriptionId}")
    @PreAuthorize("@invoiceAuthorization.canView(authentication.principal.id, #subscriptionId)")
    public List<Invoice> listBySubscription(@PathVariable UUID subscriptionId) {
        return getSubscriptionInvoicesUseCase.execute(subscriptionId);
    }

    @PostMapping("/invoices/pay/{invoiceId}")
    @PreAuthorize("@invoiceAuthorization.canPay(authentication.principal.id, #invoiceId)")
    public void payInvoice(@PathVariable UUID invoiceId) {
        payInvoiceUseCase.execute(invoiceId);
    }
}
