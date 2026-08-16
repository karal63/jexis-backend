package com.jexis.jexis_backend.invoice.application.useCases;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jexis.jexis_backend.invoice.domain.entities.Invoice;
import com.jexis.jexis_backend.invoice.infrastructure.InvoiceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAllInvoicesUseCase {
    private final InvoiceRepository invoiceRepository;

    @Transactional(readOnly = true)
    public List<Invoice> execute() {
        return invoiceRepository.findAll();
    }
}
