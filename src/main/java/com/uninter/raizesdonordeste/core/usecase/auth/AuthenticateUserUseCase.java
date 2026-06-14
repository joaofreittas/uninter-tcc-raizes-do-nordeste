package com.uninter.raizesdonordeste.core.usecase.auth;

import com.uninter.raizesdonordeste.core.exception.DomainException;
import com.uninter.raizesdonordeste.core.gateway.UserGateway;
import com.uninter.raizesdonordeste.core.input.auth.AuthenticateUserInput;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AuthenticateUserUseCase {

    private final UserGateway userGateway;
    private final AuthenticationManager authenticationManager;

    public String execute(final AuthenticateUserInput input,
        final Function<UserDetails, String> tokenGenerator) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(input.email(), input.password()));
        var user = userGateway.findByEmail(input.email())
            .orElseThrow(() -> new DomainException("User not found: " + input.email()));
        return tokenGenerator.apply(buildUserDetails(user));
    }

    private UserDetails buildUserDetails(
        com.uninter.raizesdonordeste.core.domain.user.UserDomain user) {
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
            .password(user.getPassword())
            .authorities("ROLE_" + user.getRole().name())
            .build();
    }

}
