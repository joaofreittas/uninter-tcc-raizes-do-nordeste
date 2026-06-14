package com.uninter.raizesdonordeste.core.usecase.product;

import com.uninter.raizesdonordeste.core.domain.audit.AuditAction;
import com.uninter.raizesdonordeste.core.domain.audit.Auditable;
import com.uninter.raizesdonordeste.core.domain.product.ProductDomain;
import com.uninter.raizesdonordeste.core.gateway.ProductGateway;
import com.uninter.raizesdonordeste.core.input.product.UpdateProductInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProductUseCase {

    private final FindProductByIdUseCase findProductByIdUseCase;
    private final ProductGateway productGateway;

    @Auditable(action = AuditAction.UPDATE)
    public ProductDomain execute(final UpdateProductInput input) {
        var existing = findProductByIdUseCase.execute(input.id());
        return productGateway.save(existing.update(input.name(), input.price()));
    }

}
