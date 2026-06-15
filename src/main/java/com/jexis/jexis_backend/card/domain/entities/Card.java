package com.jexis.jexis_backend.card.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.jexis.jexis_backend.cardholder.domain.entities.CardHolder;
import com.jexis.jexis_backend.user.domain.entities.User;
import com.jexis.jexis_backend.wallet.domain.entities.Wallet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Card entity mapped to the persistence layer.
 *
 * Represents a card record stored in the database and defines
 * its persistence structure (table mapping, constraints, and identifiers).
 *
 * This class is managed by JPA and is used to persist and retrieve
 * card data.
 *
 * Author: Leo
 */
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String stripeCardId;

    @ManyToOne
    @JoinColumn(name = "card_holder_id", referencedColumnName = "id", nullable = false)
    private CardHolder cardHolder;

    @ManyToOne
    @JoinColumn(name = "stripe_financial_account_id", referencedColumnName = "id", nullable = false)
    private Wallet treasuryAccount;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String last4;

    @Column(nullable = false)
    private String status;

    @Column(name = "limit_amount")
    private BigDecimal limit;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private Long expYear;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    public Card() {
    }

    public Card(String stripeCardId, CardHolder cardHolder, Wallet treasuryAccount, User user, String last4,
            String status,
            String brand, String type, String currency, Long expYear) {
        this.stripeCardId = stripeCardId;
        this.cardHolder = cardHolder;
        this.treasuryAccount = treasuryAccount;
        this.user = user;
        this.last4 = last4;
        this.status = status;
        this.brand = brand;
        this.type = type;
        this.currency = currency;
        this.expYear = expYear;
    }

    public UUID getId() {
        return id;
    }

    public CardHolder getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(CardHolder cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getLast4() {
        return last4;
    }

    public String getStatus() {
        return status;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public String getStripeCardId() {
        return stripeCardId;
    }

    public User getUser() {
        return user;
    }

    public void setStripeCardId(String stripeCardId) {
        this.stripeCardId = stripeCardId;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getExpYear() {
        return expYear;
    }

    public void setExpYear(Long expYear) {
        this.expYear = expYear;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
