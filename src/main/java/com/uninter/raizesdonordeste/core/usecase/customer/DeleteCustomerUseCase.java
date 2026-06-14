package com.uninter.raizesdonordeste.core.usecase.customer;

import com.uninter.raizesdonordeste.core.domain.audit.AuditAction;
import com.uninter.raizesdonordeste.core.domain.audit.Auditable;
import com.uninter.raizesdonordeste.core.gateway.CustomerGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCustomerUseCase {

    private final FindCustomerByIdUseCase findCustomerByIdUseCase;
    private final CustomerGateway customerGateway;

    @Auditable(action = AuditAction.DELETE)
    public void execute(final Long id) {
        var customer = findCustomerByIdUseCase.execute(id);
        customerGateway.delete(customer);
    }

}
