package com.uninter.raizesdonordeste.core.input.customer;

import java.time.LocalDate;

public record UpdateCustomerInput(
    Long id,
    String name,
    String phone,
    LocalDate birthDate,
    Boolean marketingAccepted
) {

}
