package com.uninter.raizesdonordeste.core.gateway;

import com.uninter.raizesdonordeste.core.domain.product.ProductDomain;

import java.util.List;
import java.util.Optional;

public interface ProductGateway {

    ProductDomain save(ProductDomain product);

    Optional<ProductDomain> findById(Long id);

    List<ProductDomain> findAll();

    void delete(ProductDomain product);

}
