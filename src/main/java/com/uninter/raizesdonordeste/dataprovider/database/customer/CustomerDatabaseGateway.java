package com.uninter.raizesdonordeste.dataprovider.database.customer;

import com.uninter.raizesdonordeste.core.domain.customer.CustomerDomain;
import com.uninter.raizesdonordeste.core.gateway.CustomerGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerDatabaseGateway implements CustomerGateway {

    private final CustomerRepository repository;

    @Override
    public CustomerDomain save(final CustomerDomain customer) {
        return repository.save(CustomerEntity.from(customer)).toDomain();
    }

    @Override
    public Optional<CustomerDomain> findById(final Long id) {
        return repository.findById(id).map(CustomerEntity::toDomain);
    }

    @Override
    public Optional<CustomerDomain> findByDocument(final String document) {
        return repository.findByDocument(document).map(CustomerEntity::toDomain);
    }

    @Override
    public Optional<CustomerDomain> findByEmail(final String email) {
        return repository.findByEmail(email).map(CustomerEntity::toDomain);
    }

    @Override
    public List<CustomerDomain> findAll() {
        return repository.findAll().stream().map(CustomerEntity::toDomain).toList();
    }

    @Override
    public void delete(final CustomerDomain customer) {
        repository.deleteById(customer.getId());
    }

}
