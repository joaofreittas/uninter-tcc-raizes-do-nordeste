package com.uninter.raizesdonordeste.core.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDomain {

    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;
    private Long unitId;
    private Long employeeId;

    public static UserDomain create(final String name, final String email,
                                    final String encodedPassword, final Role role) {
        return UserDomain.builder()
            .name(name)
            .email(email)
            .password(encodedPassword)
            .role(role == null ? Role.USER : role)
            .build();
    }

}
