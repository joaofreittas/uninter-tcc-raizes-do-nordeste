package com.uninter.raizesdonordeste.core.domain.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDomain {

    private Long id;
    private Long customerId;
    private Long unitId;
    private DeliveryType deliveryType;
    private OrderChannel orderChannel;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderItemDomain> items;

    public static OrderDomain create(final Long customerId, 
                                     final Long unitId,
                                     final DeliveryType deliveryType,
                                     final OrderChannel orderChannel, 
                                     final BigDecimal totalAmount) {
        return OrderDomain.builder()
            .customerId(customerId)
            .unitId(unitId)
            .deliveryType(deliveryType)
            .orderChannel(orderChannel)
            .status(OrderStatus.PENDING)
            .totalAmount(totalAmount)
            .createdAt(LocalDateTime.now())
            .build();
    }

    public void confirmPayment() {
        this.status = OrderStatus.PAYMENT_CONFIRMED;
        this.updatedAt = LocalDateTime.now();
    }

    public void confirm() {
        this.status = OrderStatus.CONFIRMED;
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel() {
        this.status = OrderStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }

}
