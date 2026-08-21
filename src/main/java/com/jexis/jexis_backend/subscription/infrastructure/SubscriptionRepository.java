package com.jexis.jexis_backend.subscription.infrastructure;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jexis.jexis_backend.subscription.domain.entities.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    Optional<Subscription> findByStripeSubscriptionId(String stripeSubscriptionId);
}
