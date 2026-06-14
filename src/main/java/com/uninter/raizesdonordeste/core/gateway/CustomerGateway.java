package com.uninter.raizesdonordeste.core.gateway;

import com.uninter.raizesdonordeste.core.domain.customer.CustomerDomain;

import java.util.List;
import java.util.Optional;

public interface CustomerGateway {

    CustomerDomain save(CustomerDomain customer);

    Optional<CustomerDomain> findById(Long id);

    Optional<CustomerDomain> findByDocument(String document);

    Optional<CustomerDomain> findByEmail(String email);

    List<CustomerDomain> findAll();

    void delete(CustomerDomain customer);

}
