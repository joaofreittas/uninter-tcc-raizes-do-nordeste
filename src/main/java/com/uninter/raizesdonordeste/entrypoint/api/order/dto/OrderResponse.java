package com.uninter.raizesdonordeste.entrypoint.api.order.dto;

import com.uninter.raizesdonordeste.core.domain.order.DeliveryType;
import com.uninter.raizesdonordeste.core.domain.order.OrderChannel;
import com.uninter.raizesdonordeste.core.domain.order.OrderDomain;
import com.uninter.raizesdonordeste.core.domain.order.OrderItemDomain;
import com.uninter.raizesdonordeste.core.domain.order.OrderStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderResponse(
    Long id,
    Long customerId,
    Long unitId,
    DeliveryType deliveryType,
    OrderChannel orderChannel,
    OrderStatus status,
    BigDecimal totalAmount,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    List<OrderItemResponse> items
) {

    @Builder
    public record OrderItemResponse(
        Long id,
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice
    ) {

        public static OrderItemResponse fromDomain(final OrderItemDomain domain) {
            return OrderItemResponse.builder()
                .id(domain.getId())
                .productId(domain.getProductId())
                .productName(domain.getProductName())
                .quantity(domain.getQuantity())
                .unitPrice(domain.getUnitPrice())
                .build();
        }

    }

    public static OrderResponse fromDomain(final OrderDomain domain) {
        var items = domain.getItems() == null ? List.<OrderItemResponse>of()
            : domain.getItems().stream().map(OrderItemResponse::fromDomain).toList();
        return OrderResponse.builder()
            .id(domain.getId())
            .customerId(domain.getCustomerId())
            .unitId(domain.getUnitId())
            .deliveryType(domain.getDeliveryType())
            .orderChannel(domain.getOrderChannel())
            .status(domain.getStatus())
            .totalAmount(domain.getTotalAmount())
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .items(items)
            .build();
    }

}
