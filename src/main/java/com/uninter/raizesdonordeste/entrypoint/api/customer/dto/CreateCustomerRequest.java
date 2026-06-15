package com.uninter.raizesdonordeste.entrypoint.api.customer.dto;

import com.uninter.raizesdonordeste.core.input.customer.CreateCustomerInput;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateCustomerRequest(
    @NotBlank String name,
    @NotBlank String document,
    @NotBlank @Email String email,
    @NotBlank @Size(min = 8) String password,
    String phone,
    LocalDate birthDate,
    @NotNull Boolean lgpdAccepted,
    Boolean marketingAccepted
) {

    public CreateCustomerInput toInput() {
        return new CreateCustomerInput(
            name,
            document,
            email,
            password,
            phone,
            birthDate,
            lgpdAccepted,
            marketingAccepted != null ? marketingAccepted : Boolean.FALSE
        );
    }

}
