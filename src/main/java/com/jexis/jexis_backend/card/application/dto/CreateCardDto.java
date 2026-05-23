package com.jexis.jexis_backend.card.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateCardDto {
    private UUID userId;
    private String last4;
    private String status;
    private BigDecimal limit;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }
}
