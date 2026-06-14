package com.uninter.raizesdonordeste.dataprovider.database.order;

import com.uninter.raizesdonordeste.core.domain.order.DeliveryType;
import com.uninter.raizesdonordeste.core.domain.order.OrderChannel;
import com.uninter.raizesdonordeste.core.domain.order.OrderDomain;
import com.uninter.raizesdonordeste.core.domain.order.OrderStatus;
import com.uninter.raizesdonordeste.dataprovider.database.customer.CustomerEntity;
import com.uninter.raizesdonordeste.dataprovider.database.unit.UnitEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private UnitEntity unit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryType deliveryType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderChannel orderChannel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order")
    private List<OrderItemEntity> items;

    public OrderDomain toDomain() {
        var itemDomains = items == null ? List.<com.uninter.raizesdonordeste.core.domain.order.OrderItemDomain>of()
            : items.stream().map(OrderItemEntity::toDomain).toList();
        return OrderDomain.builder()
            .id(id)
            .customerId(customer != null ? customer.getId() : null)
            .unitId(unit != null ? unit.getId() : null)
            .deliveryType(deliveryType)
            .orderChannel(orderChannel)
            .status(status)
            .totalAmount(totalAmount)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .items(itemDomains)
            .build();
    }

    public static OrderEntity from(final OrderDomain domain, final CustomerEntity customerEntity,
                                   final UnitEntity unitEntity) {
        return OrderEntity.builder()
            .id(domain.getId())
            .customer(customerEntity)
            .unit(unitEntity)
            .deliveryType(domain.getDeliveryType())
            .orderChannel(domain.getOrderChannel())
            .status(domain.getStatus())
            .totalAmount(domain.getTotalAmount())
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .build();
    }

}
