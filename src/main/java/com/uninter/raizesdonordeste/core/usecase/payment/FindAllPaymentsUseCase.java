package com.uninter.raizesdonordeste.core.usecase.payment;

import com.uninter.raizesdonordeste.core.domain.payment.PaymentDomain;
import com.uninter.raizesdonordeste.core.gateway.PaymentGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllPaymentsUseCase {

    private final PaymentGateway paymentGateway;

    public List<PaymentDomain> execute() {
        return paymentGateway.findAll();
    }

}
