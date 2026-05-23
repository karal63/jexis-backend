package com.jexis.jexis_backend.card.domain.entities;

import java.math.BigDecimal;
import java.util.UUID;

import com.jexis.jexis_backend.user.domain.entities.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(nullable = false, length = 4)
    private String last4;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false, precision = 19, scale = 2, name = "limit_amount")
    private BigDecimal limit;

    public Card() {
    }

    public Card(User user, String last4, String status, BigDecimal limit) {
        this.user = user;
        this.last4 = last4;
        this.status = status;
        this.limit = limit;
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
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
}
