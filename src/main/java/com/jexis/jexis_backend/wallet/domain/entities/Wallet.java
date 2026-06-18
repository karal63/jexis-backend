package com.jexis.jexis_backend.wallet.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.jexis.jexis_backend.account.domain.entities.Account;

import jakarta.persistence.*;

/**
 * Wallet entity mapped to the persistence layer.
 *
 * Represents a wallet record stored in the database and defines
 * its persistence structure (table mapping, constraints, and identifiers).
 *
 * This class is managed by JPA and is used to persist and retrieve
 * wallet data.
 *
 * Author: Leo
 */
@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String stripeFinancialAccountId;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account account;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal availableBalance = BigDecimal.ZERO;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    private LocalDateTime deletedAt;

    Wallet() {
    }

    public Wallet(String name, String stripeFinancialAccountId, Account account) {
        this.name = name;
        this.stripeFinancialAccountId = stripeFinancialAccountId;
        this.account = account;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getStripeFinancialAccountId() {
        return stripeFinancialAccountId;
    }

    public Account getAccount() {
        return account;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String setStripeFinancialAccountId() {
        return stripeFinancialAccountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
