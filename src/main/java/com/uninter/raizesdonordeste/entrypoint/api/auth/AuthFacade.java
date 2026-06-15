package com.uninter.raizesdonordeste.entrypoint.api.auth;

import com.uninter.raizesdonordeste.config.auth.JWTService;
import com.uninter.raizesdonordeste.core.input.auth.AuthenticateUserInput;
import com.uninter.raizesdonordeste.core.input.auth.RegisterUserInput;
import com.uninter.raizesdonordeste.core.usecase.auth.AuthenticateUserUseCase;
import com.uninter.raizesdonordeste.core.usecase.auth.RegisterUserUseCase;
import com.uninter.raizesdonordeste.entrypoint.api.auth.dto.AuthRequest;
import com.uninter.raizesdonordeste.entrypoint.api.auth.dto.AuthResponse;
import com.uninter.raizesdonordeste.entrypoint.api.auth.dto.RegisterRequest;
import com.uninter.raizesdonordeste.entrypoint.api.auth.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final RegisterUserUseCase registerUserUseCase;
    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final JWTService jwtService;

    public UserResponse register(final RegisterRequest request) {
        var domain = registerUserUseCase.execute(
            new RegisterUserInput(request.name(), request.email(), request.password(), request.role()));
        return UserResponse.fromDomain(domain);
    }

    public AuthResponse authenticate(final AuthRequest request) {
        var token = authenticateUserUseCase.execute(
            new AuthenticateUserInput(request.email(), request.password()),
            jwtService::generateToken);
        return new AuthResponse(token);
    }

}
