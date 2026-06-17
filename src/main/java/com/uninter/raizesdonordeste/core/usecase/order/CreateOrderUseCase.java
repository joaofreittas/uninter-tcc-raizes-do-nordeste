package com.uninter.raizesdonordeste.core.usecase.order;

import com.uninter.raizesdonordeste.core.domain.audit.AuditAction;
import com.uninter.raizesdonordeste.core.domain.audit.Auditable;
import com.uninter.raizesdonordeste.core.domain.order.OrderCreatedEvent;
import com.uninter.raizesdonordeste.core.domain.order.OrderDomain;
import com.uninter.raizesdonordeste.core.domain.order.OrderItemDomain;
import com.uninter.raizesdonordeste.core.domain.product.ProductDomain;
import com.uninter.raizesdonordeste.core.exception.ResourceNotFoundException;
import com.uninter.raizesdonordeste.core.gateway.CustomerGateway;
import com.uninter.raizesdonordeste.core.gateway.OrderGateway;
import com.uninter.raizesdonordeste.core.gateway.OrderItemGateway;
import com.uninter.raizesdonordeste.core.gateway.ProductGateway;
import com.uninter.raizesdonordeste.core.gateway.UnitGateway;
import com.uninter.raizesdonordeste.core.input.order.CreateOrderInput;
import com.uninter.raizesdonordeste.core.input.order.OrderItemInput;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCase {

    private final CustomerGateway customerGateway;
    private final UnitGateway unitGateway;
    private final ProductGateway productGateway;
    private final OrderGateway orderGateway;
    private final OrderItemGateway orderItemGateway;
    private final ApplicationEventPublisher eventPublisher;

    @Auditable(action = AuditAction.CREATE)
    public OrderDomain execute(final CreateOrderInput input) {
        customerGateway.findById(input.customerId())
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + input.customerId()));

        unitGateway.findById(input.unitId())
            .orElseThrow(() -> new ResourceNotFoundException("Unit not found: " + input.unitId()));

        var resolvedItems = resolveItems(input.items());

        var totalAmount = resolvedItems.stream()
            .map(r -> r.product().getPrice().multiply(BigDecimal.valueOf(r.quantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        var savedOrder = orderGateway.save(
            OrderDomain.create(input.customerId(), input.unitId(),
                input.deliveryType(), input.orderChannel(), totalAmount));

        var savedItems = resolvedItems.stream()
            .map(r -> orderItemGateway.save(
                OrderItemDomain.create(savedOrder.getId(), r.product().getId(),
                    r.product().getName(), r.quantity(), r.product().getPrice())))
            .toList();

        eventPublisher.publishEvent(new OrderCreatedEvent(savedOrder.getId(), input.paymentType()));

        return OrderDomain.builder()
            .id(savedOrder.getId())
            .customerId(savedOrder.getCustomerId())
            .unitId(savedOrder.getUnitId())
            .deliveryType(savedOrder.getDeliveryType())
            .orderChannel(savedOrder.getOrderChannel())
            .status(savedOrder.getStatus())
            .totalAmount(savedOrder.getTotalAmount())
            .createdAt(savedOrder.getCreatedAt())
            .updatedAt(savedOrder.getUpdatedAt())
            .items(savedItems)
            .build();
    }

    private List<ResolvedItem> resolveItems(final List<OrderItemInput> items) {
        return items.stream()
            .map(itemInput -> {
                var product = productGateway.findById(itemInput.productId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found: " + itemInput.productId()));
                return new ResolvedItem(product, itemInput.quantity());
            })
            .toList();
    }

    private record ResolvedItem(ProductDomain product, Integer quantity) {

    }

}
