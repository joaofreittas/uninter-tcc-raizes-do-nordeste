package com.uninter.raizesdonordeste.core.usecase.customer;

import com.uninter.raizesdonordeste.core.domain.customer.CustomerDomain;
import com.uninter.raizesdonordeste.core.gateway.CustomerGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllCustomersUseCase {

    private final CustomerGateway customerGateway;

    public List<CustomerDomain> execute() {
        return customerGateway.findAll();
    }

}
