package com.uninter.raizesdonordeste.dataprovider.database.order;

import com.uninter.raizesdonordeste.core.domain.order.OrderItemDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    public OrderItemDomain toDomain() {
        return OrderItemDomain.builder()
            .id(id)
            .orderId(order != null ? order.getId() : null)
            .productId(productId)
            .productName(productName)
            .quantity(quantity)
            .unitPrice(unitPrice)
            .build();
    }

    public static OrderItemEntity from(final OrderItemDomain domain, final OrderEntity orderEntity) {
        return OrderItemEntity.builder()
            .id(domain.getId())
            .order(orderEntity)
            .productId(domain.getProductId())
            .productName(domain.getProductName())
            .quantity(domain.getQuantity())
            .unitPrice(domain.getUnitPrice())
            .build();
    }

}
