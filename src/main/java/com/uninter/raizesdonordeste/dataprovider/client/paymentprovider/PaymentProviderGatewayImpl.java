package com.uninter.raizesdonordeste.dataprovider.client.paymentprovider;

import com.uninter.raizesdonordeste.core.domain.payment.PaymentProviderResult;
import com.uninter.raizesdonordeste.core.gateway.PaymentProviderGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentProviderGatewayImpl implements PaymentProviderGateway {

    private final PaymentProviderFeignClient feignClient;

    @Override
    public PaymentProviderResult processPayment(final BigDecimal amount) {
        var transactionId = UUID.randomUUID().toString();
        var response = feignClient.charge(new PaymentProviderRequest(amount, transactionId));
        return new PaymentProviderResult(response.gatewayTransactionId(), response.amount(), response.status());
    }

}
