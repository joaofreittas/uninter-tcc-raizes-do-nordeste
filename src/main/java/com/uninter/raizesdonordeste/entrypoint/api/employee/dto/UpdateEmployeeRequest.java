package com.uninter.raizesdonordeste.entrypoint.api.employee.dto;

import com.uninter.raizesdonordeste.core.domain.employee.EmployeeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdateEmployeeRequest(
    @NotBlank String name,
    String address,
    LocalDate birthDate,
    @NotNull EmployeeType type
) {

}
