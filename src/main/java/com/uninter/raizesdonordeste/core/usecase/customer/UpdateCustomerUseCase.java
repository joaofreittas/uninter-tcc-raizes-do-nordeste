package com.uninter.raizesdonordeste.core.usecase.customer;

import com.uninter.raizesdonordeste.core.domain.audit.AuditAction;
import com.uninter.raizesdonordeste.core.domain.audit.Auditable;
import com.uninter.raizesdonordeste.core.domain.customer.CustomerDomain;
import com.uninter.raizesdonordeste.core.gateway.CustomerGateway;
import com.uninter.raizesdonordeste.core.input.customer.UpdateCustomerInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCustomerUseCase {

    private final FindCustomerByIdUseCase findCustomerByIdUseCase;
    private final CustomerGateway customerGateway;

    @Auditable(action = AuditAction.UPDATE)
    public CustomerDomain execute(final UpdateCustomerInput input) {
        var existing = findCustomerByIdUseCase.execute(input.id());
        return customerGateway.save(
            existing.update(input.name(), input.phone(), input.birthDate(), input.marketingAccepted()));
    }

}
