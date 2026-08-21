package com.jexis.jexis_backend.subscription.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.jexis.jexis_backend.account.domain.entities.Account;
import com.jexis.jexis_backend.plan.domain.entities.Plan;
import com.jexis.jexis_backend.subscription.domain.enums.SubscriptionStatus;
import com.stripe.param.SubscriptionUpdateParams;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.jexis.jexis_backend.plan.domain.entities.Price;
import com.jexis.jexis_backend.user.domain.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
@NoArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @ManyToOne
    @JoinColumn(name = "price_id", nullable = false)
    private Price price;

    @Column(nullable = false, unique = true)
    private String stripeSubscriptionId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    @Column(nullable = false)
    private LocalDateTime currentPeriodStart;

    @Column(nullable = false)
    private LocalDateTime currentPeriodEnd;

    @Column(nullable = false)
    private boolean cancelAtPeriodEnd = false;

    private LocalDateTime canceledAt;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Subscription(User user, Account account, Plan plan, Price price, String stripeSubscriptionId, SubscriptionStatus status, LocalDateTime currentPeriodStart, LocalDateTime currentPeriodEnd, boolean cancelAtPeriodEnd) {
        this.user = user;
        this.account = account;
        this.plan = plan;
        this.price = price;
        this.stripeSubscriptionId = stripeSubscriptionId;
        this.status = status;
        this.currentPeriodStart = currentPeriodStart;
        this.currentPeriodEnd = currentPeriodEnd;
        this.cancelAtPeriodEnd = cancelAtPeriodEnd;
    }
}
