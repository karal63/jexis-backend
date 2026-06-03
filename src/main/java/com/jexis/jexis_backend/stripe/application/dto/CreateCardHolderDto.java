package com.jexis.jexis_backend.stripe.application.dto;

/**
 * CreateCardHolderDto
 *
 * Data Transfer Object used for card holder creation requests. It encapsulates
 * the
 * necessary data required to create a new card holder, such as the connected
 * account ID.
 *
 * Author: Leo
 */
public record CreateCardHolderDto(String connectedAccountId) {
}
