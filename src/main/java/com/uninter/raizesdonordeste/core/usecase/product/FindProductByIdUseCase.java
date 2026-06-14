package com.uninter.raizesdonordeste.core.usecase.product;

import com.uninter.raizesdonordeste.core.domain.product.ProductDomain;
import com.uninter.raizesdonordeste.core.exception.ResourceNotFoundException;
import com.uninter.raizesdonordeste.core.gateway.ProductGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindProductByIdUseCase {

    private final ProductGateway productGateway;

    public ProductDomain execute(final Long id) {
        return productGateway.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }

}
