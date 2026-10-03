package com.jexis.jexis_backend.invoice.infrastructure;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jexis.jexis_backend.invoice.domain.entities.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
    List<Invoice> findBySubscriptionId(UUID subscriptionId);

    Optional<Invoice> findByStripeInvoiceId(String stripeInvoiceId);
}
