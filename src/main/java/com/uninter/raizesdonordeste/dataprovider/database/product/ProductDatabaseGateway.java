package com.uninter.raizesdonordeste.dataprovider.database.product;

import com.uninter.raizesdonordeste.core.domain.product.ProductDomain;
import com.uninter.raizesdonordeste.core.gateway.ProductGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductDatabaseGateway implements ProductGateway {

    private final ProductRepository repository;

    @Override
    public ProductDomain save(final ProductDomain product) {
        return repository.save(ProductEntity.from(product)).toDomain();
    }

    @Override
    public Optional<ProductDomain> findById(final Long id) {
        return repository.findById(id).map(ProductEntity::toDomain);
    }

    @Override
    public List<ProductDomain> findAll() {
        return repository.findAll().stream().map(ProductEntity::toDomain).toList();
    }

    @Override
    public void delete(final ProductDomain product) {
        repository.deleteById(product.getId());
    }

}
