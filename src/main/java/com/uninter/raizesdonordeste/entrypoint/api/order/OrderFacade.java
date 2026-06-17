package com.uninter.raizesdonordeste.entrypoint.api.order;

import com.uninter.raizesdonordeste.core.input.order.CreateOrderInput;
import com.uninter.raizesdonordeste.core.input.order.OrderItemInput;
import com.uninter.raizesdonordeste.core.usecase.order.CreateOrderUseCase;
import com.uninter.raizesdonordeste.entrypoint.api.order.dto.CreateOrderRequest;
import com.uninter.raizesdonordeste.entrypoint.api.order.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final CreateOrderUseCase createOrderUseCase;

    public OrderResponse create(final CreateOrderRequest request) {
        var items = request.items().stream()
            .map(i -> new OrderItemInput(i.productId(), i.quantity()))
            .toList();
        return OrderResponse.fromDomain(createOrderUseCase.execute(
            new CreateOrderInput(request.customerId(), request.unitId(),
                request.deliveryType(), request.orderChannel(),
                request.paymentType(), items)));
    }

}
