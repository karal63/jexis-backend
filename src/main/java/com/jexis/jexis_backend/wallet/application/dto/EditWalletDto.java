package com.jexis.jexis_backend.wallet.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * EditWalletDto
 *
 * Data Transfer Object used for wallet editing requests. It encapsulates the
 * necessary data required to edit an existing wallet, such as the wallet name
 * and
 * owner ID.
 *
 * Author: Leo
 */
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
