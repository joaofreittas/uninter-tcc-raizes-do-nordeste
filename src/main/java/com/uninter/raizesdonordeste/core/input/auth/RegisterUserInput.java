package com.uninter.raizesdonordeste.core.input.auth;

import com.uninter.raizesdonordeste.core.domain.user.Role;

public record RegisterUserInput(String name, String email, String rawPassword, Role role) {

}
