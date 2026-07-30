package com.jexis.jexis_backend.dispute.infrastructure;

import com.jexis.jexis_backend.dispute.domain.entities.Dispute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DisputeRepository extends JpaRepository<Dispute, UUID> {
    Optional<Dispute> findByStripeDisputeId(String stripeDisputeId);

    List<Dispute> findAllByTransaction_Card_Id(UUID cardId);
}
