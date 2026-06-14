package com.uninter.raizesdonordeste.core.input.employee;

import com.uninter.raizesdonordeste.core.domain.employee.EmployeeType;

import java.time.LocalDate;

public record UpdateEmployeeInput(
    Long id,
    String name,
    String address,
    LocalDate birthDate,
    EmployeeType type
) {

}
