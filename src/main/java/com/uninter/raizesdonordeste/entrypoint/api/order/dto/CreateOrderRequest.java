package com.uninter.raizesdonordeste.entrypoint.api.order.dto;

import com.uninter.raizesdonordeste.core.domain.order.DeliveryType;
import com.uninter.raizesdonordeste.core.domain.order.OrderChannel;
import com.uninter.raizesdonordeste.core.domain.payment.PaymentType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateOrderRequest(
    @NotNull Long customerId,
    @NotNull Long unitId,
    @NotNull DeliveryType deliveryType,
    @NotNull OrderChannel orderChannel,
    @NotNull PaymentType paymentType,
    @NotNull @Size(min = 1, message = "O pedido deve ter ao menos um item") @Valid List<OrderItemRequest> items
) {

    public record OrderItemRequest(
        @NotNull Long productId,
        @NotNull @Min(value = 1, message = "A quantidade mínima por item é 1") Integer quantity
    ) {

    }

}
