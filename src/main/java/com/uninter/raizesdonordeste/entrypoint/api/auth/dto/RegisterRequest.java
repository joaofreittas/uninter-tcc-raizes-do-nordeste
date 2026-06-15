package com.uninter.raizesdonordeste.entrypoint.api.auth.dto;

import com.uninter.raizesdonordeste.core.domain.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank String name,
    @Email String email,
    @Size(min = 8) String password,
    Role role
) {

}
