package com.jexis.jexis_backend.card.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.jexis.jexis_backend.cardholder.domain.entities.CardHolder;

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

    @ManyToOne
    @JoinColumn(name = "card_holder_id", referencedColumnName = "id", nullable = false)
    private CardHolder cardHolder;

    @Column(nullable = false, length = 4)
    private String last4;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false, precision = 19, scale = 2, name = "limit_amount")
    private BigDecimal limit;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(nullable = false)
    private Integer expYear;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    public Card() {
    }

    public Card(CardHolder cardHolder, String last4, String status, BigDecimal limit, String brand, String type,
            String currency, Integer expYear) {
        this.cardHolder = cardHolder;
        this.last4 = last4;
        this.status = status;
        this.limit = limit;
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

    public Integer getExpYear() {
        return expYear;
    }

    public void setExpYear(Integer expYear) {
        this.expYear = expYear;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
