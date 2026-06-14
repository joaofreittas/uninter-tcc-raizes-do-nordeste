package com.uninter.raizesdonordeste.core.usecase.auth;

import com.uninter.raizesdonordeste.core.domain.user.UserDomain;
import com.uninter.raizesdonordeste.core.gateway.UserGateway;
import com.uninter.raizesdonordeste.core.input.auth.RegisterUserInput;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final UserGateway userGateway;
    private final PasswordEncoder passwordEncoder;

    public UserDomain execute(final RegisterUserInput input) {
        return userGateway.save(
            UserDomain.create(input.name(), input.email(),
                passwordEncoder.encode(input.rawPassword()), input.role()));
    }

}
