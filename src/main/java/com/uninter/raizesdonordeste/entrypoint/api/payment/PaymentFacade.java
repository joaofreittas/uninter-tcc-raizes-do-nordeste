package com.uninter.raizesdonordeste.entrypoint.api.payment;

import com.uninter.raizesdonordeste.core.usecase.payment.FindAllPaymentsUseCase;
import com.uninter.raizesdonordeste.entrypoint.api.payment.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentFacade {

    private final FindAllPaymentsUseCase findAllPaymentsUseCase;

    public List<PaymentResponse> findAll() {
        return findAllPaymentsUseCase.execute().stream()
            .map(PaymentResponse::fromDomain)
            .toList();
    }

}
