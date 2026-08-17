package com.jexis.jexis_backend.plan.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePlanEntitlementDto {
    @NotNull
    private String value;
}
