package com.uninter.raizesdonordeste.core.domain.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDomain {

    private Long id;
    private Long orderId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;

    public static OrderItemDomain create(final Long orderId, 
                                         final Long productId,
                                         final String productName, 
                                         final Integer quantity,
                                         final BigDecimal unitPrice) {
        return OrderItemDomain.builder()
            .orderId(orderId)
            .productId(productId)
            .productName(productName)
            .quantity(quantity)
            .unitPrice(unitPrice)
            .build();
    }

}
