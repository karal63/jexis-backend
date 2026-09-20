package com.jexis.jexis_backend.subscription.infrastructure;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jexis.jexis_backend.subscription.domain.entities.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    Optional<Subscription> findByStripeSubscriptionId(String stripeSubscriptionId);

    List<Subscription> findByUserId(UUID userId);

    @Query("""
                SELECT s
                FROM Subscription s
                WHERE s.account.id = :accountId
                  AND s.currentPeriodStart <= :now
                  AND s.currentPeriodEnd > :now
                  AND s.status != SubscriptionStatus.CANCELED
            """)
    Subscription findActiveSubscription(
            UUID accountId,
            LocalDateTime now
    );
}
