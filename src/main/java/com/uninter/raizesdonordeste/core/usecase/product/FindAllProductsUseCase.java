package com.uninter.raizesdonordeste.core.usecase.product;

import com.uninter.raizesdonordeste.core.domain.product.ProductDomain;
import com.uninter.raizesdonordeste.core.gateway.ProductGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllProductsUseCase {

    private final ProductGateway productGateway;

    public List<ProductDomain> execute() {
        return productGateway.findAll();
    }

}
