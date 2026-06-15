package com.uninter.raizesdonordeste.entrypoint.api.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateProductRequest(
    @NotBlank String name,
    @NotNull @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero") BigDecimal price
) {

}
