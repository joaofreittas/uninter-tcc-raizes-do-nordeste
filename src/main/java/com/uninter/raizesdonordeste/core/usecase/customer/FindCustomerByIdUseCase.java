package com.uninter.raizesdonordeste.core.usecase.customer;

import com.uninter.raizesdonordeste.core.domain.customer.CustomerDomain;
import com.uninter.raizesdonordeste.core.exception.ResourceNotFoundException;
import com.uninter.raizesdonordeste.core.gateway.CustomerGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindCustomerByIdUseCase {

    private final CustomerGateway customerGateway;

    public CustomerDomain execute(final Long id) {
        return customerGateway.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
    }

}
