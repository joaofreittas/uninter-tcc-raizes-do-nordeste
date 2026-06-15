package com.uninter.raizesdonordeste.dataprovider.client.paymentprovider;

import java.math.BigDecimal;

public record PaymentProviderRequest(
    BigDecimal amount,
    String gatewayTransactionId
) {

}
