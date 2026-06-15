package com.uninter.raizesdonordeste.entrypoint.api.auth.dto;

import com.uninter.raizesdonordeste.core.domain.user.Role;
import com.uninter.raizesdonordeste.core.domain.user.UserDomain;
import lombok.Builder;

@Builder
public record UserResponse(Long id, String name, String email, Role role) {

    public static UserResponse fromDomain(final UserDomain domain) {
        return UserResponse.builder()
            .id(domain.getId())
            .name(domain.getName())
            .email(domain.getEmail())
            .role(domain.getRole())
            .build();
    }

}
