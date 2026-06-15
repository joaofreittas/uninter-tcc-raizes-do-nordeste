package com.uninter.raizesdonordeste.dataprovider.client.paymentprovider;

import java.math.BigDecimal;

public record PaymentProviderResponse(
    String gatewayTransactionId,
    BigDecimal amount,
    String status
) {

}
