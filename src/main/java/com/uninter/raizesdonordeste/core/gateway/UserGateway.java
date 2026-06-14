package com.uninter.raizesdonordeste.core.gateway;

import com.uninter.raizesdonordeste.core.domain.user.UserDomain;

import java.util.Optional;

public interface UserGateway {

    UserDomain save(UserDomain user);

    Optional<UserDomain> findByEmail(String email);

}
