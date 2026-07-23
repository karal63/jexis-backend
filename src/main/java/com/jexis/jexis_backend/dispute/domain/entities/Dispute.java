package com.jexis.jexis_backend.dispute.domain.entities;

import com.jexis.jexis_backend.dispute.domain.enums.DisputeStatus;
import com.jexis.jexis_backend.transaction.domain.entities.Transaction;
import com.jexis.jexis_backend.wallet.domain.entities.Wallet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "disputes")
public class Dispute {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String stripeDisputeId;

    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "transaction_id", referencedColumnName = "id", nullable = false, unique = true)
    private Transaction transaction;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "wallet_id", referencedColumnName = "id", nullable = false)
    private Wallet wallet;

    @Getter
    @Setter
    @Column(nullable = false)
    private Long amount;

    @Getter
    @Setter
    @Column(nullable = false)
    private String currency;

    @Getter
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DisputeStatus status;

    @Getter
    @Setter
    private String reason;

    @Getter
    @Setter
    @Column
    private String evidenceDueBy;

    @Getter
    @Setter
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Getter
    @Setter
    private LocalDateTime resolvedAt;
}
