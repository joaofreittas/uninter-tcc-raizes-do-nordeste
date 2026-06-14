package com.uninter.raizesdonordeste.dataprovider.database.auth;

import com.uninter.raizesdonordeste.core.domain.user.UserDomain;
import com.uninter.raizesdonordeste.core.gateway.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDatabaseGateway implements UserGateway {

    private final UserRepository repository;

    @Override
    public UserDomain save(final UserDomain user) {
        return repository.save(UserEntity.from(user)).toDomain();
    }

    @Override
    public Optional<UserDomain> findByEmail(final String email) {
        return repository.findByEmail(email).map(UserEntity::toDomain);
    }

}
