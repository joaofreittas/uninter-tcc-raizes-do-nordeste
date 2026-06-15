package com.uninter.raizesdonordeste.core.gateway;

import com.uninter.raizesdonordeste.core.domain.payment.PaymentProviderResult;

import java.math.BigDecimal;

public interface PaymentProviderGateway {

    PaymentProviderResult processPayment(BigDecimal amount);

}
