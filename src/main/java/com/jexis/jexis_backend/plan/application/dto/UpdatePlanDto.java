package com.jexis.jexis_backend.plan.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePlanDto {
    @NotBlank
    private String name;

    @NotBlank
    private String code;

    private String description;
}
