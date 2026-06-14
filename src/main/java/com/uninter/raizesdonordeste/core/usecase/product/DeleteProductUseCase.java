package com.uninter.raizesdonordeste.core.usecase.product;

import com.uninter.raizesdonordeste.core.domain.audit.AuditAction;
import com.uninter.raizesdonordeste.core.domain.audit.Auditable;
import com.uninter.raizesdonordeste.core.gateway.ProductGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteProductUseCase {

    private final FindProductByIdUseCase findProductByIdUseCase;
    private final ProductGateway productGateway;

    @Auditable(action = AuditAction.DELETE)
    public void execute(final Long id) {
        var product = findProductByIdUseCase.execute(id);
        productGateway.delete(product);
    }

}
