package com.uninter.raizesdonordeste.core.usecase.order;

import com.uninter.raizesdonordeste.core.exception.ResourceNotFoundException;
import com.uninter.raizesdonordeste.core.gateway.InventoryItemGateway;
import com.uninter.raizesdonordeste.core.gateway.OrderGateway;
import com.uninter.raizesdonordeste.core.gateway.OrderItemGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeductOrderStockUseCase {

    private final OrderGateway orderGateway;
    private final OrderItemGateway orderItemGateway;
    private final InventoryItemGateway inventoryItemGateway;

    public void execute(final Long orderId) {
        var order = orderGateway.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));

        var items = orderItemGateway.findByOrderId(orderId);

        items.forEach(item -> {
            var inventoryItem = inventoryItemGateway
                .findByUnitIdAndProductId(order.getUnitId(), item.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Inventory item not found for product: " + item.getProductId()
                        + " in unit: " + order.getUnitId()));

            inventoryItemGateway.save(inventoryItem.removeStock(item.getQuantity()));
        });

        order.confirm();
        orderGateway.save(order);
    }

}
