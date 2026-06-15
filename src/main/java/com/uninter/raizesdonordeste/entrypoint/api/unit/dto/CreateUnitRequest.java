package com.uninter.raizesdonordeste.entrypoint.api.unit.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateUnitRequest(
    @NotBlank String name,
    @NotBlank String address
) {

}
