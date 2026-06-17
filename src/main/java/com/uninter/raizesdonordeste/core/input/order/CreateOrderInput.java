package com.uninter.raizesdonordeste.core.input.order;

import com.uninter.raizesdonordeste.core.domain.order.DeliveryType;
import com.uninter.raizesdonordeste.core.domain.order.OrderChannel;
import com.uninter.raizesdonordeste.core.domain.payment.PaymentType;

import java.util.List;

public record CreateOrderInput(
    Long customerId,
    Long unitId,
    DeliveryType deliveryType,
    OrderChannel orderChannel,
    PaymentType paymentType,
    List<OrderItemInput> items
) {

}
