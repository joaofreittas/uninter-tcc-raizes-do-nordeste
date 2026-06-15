package com.uninter.raizesdonordeste.core.domain.payment;

import java.math.BigDecimal;

public record PaymentProviderResult(
    String gatewayTransactionId,
    BigDecimal amount,
    String status
) {

}
