package com.jexis.jexis_backend.wallet.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EditWalletDto {
    private BigDecimal availableBalance;
    private Boolean isDeleted;
    private LocalDateTime deletedAt;

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }
}
