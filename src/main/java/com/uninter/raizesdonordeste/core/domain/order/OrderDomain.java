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

}
