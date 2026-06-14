package com.uninter.raizesdonordeste.core.usecase.product;

import com.uninter.raizesdonordeste.core.domain.audit.AuditAction;
import com.uninter.raizesdonordeste.core.domain.audit.Auditable;
import com.uninter.raizesdonordeste.core.domain.product.ProductDomain;
import com.uninter.raizesdonordeste.core.gateway.ProductGateway;
import com.uninter.raizesdonordeste.core.input.product.CreateProductInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProductUseCase {

    private final ProductGateway productGateway;

    @Auditable(action = AuditAction.CREATE)
    public ProductDomain execute(final CreateProductInput input) {
        return productGateway.save(ProductDomain.create(input.name(), input.price()));
    }

}
