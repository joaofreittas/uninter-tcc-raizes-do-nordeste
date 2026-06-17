package com.uninter.raizesdonordeste.core.domain.order;

import com.uninter.raizesdonordeste.core.domain.payment.PaymentType;

public record OrderCreatedEvent(
    Long orderId,
    PaymentType paymentType
) {

}
