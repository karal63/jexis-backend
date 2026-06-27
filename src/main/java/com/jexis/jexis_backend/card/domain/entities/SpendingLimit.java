package com.jexis.jexis_backend.card.domain.entities;

import com.jexis.jexis_backend.card.domain.enums.SpendingInterval;

public class SpendingLimit {
    private int amount;
    private SpendingInterval interval;

    public int getAmount() {
        return amount;
    }

    public SpendingInterval getInterval() {
        return interval;
    }
}
