package com.uninter.raizesdonordeste.dataprovider.database.payment;

import com.uninter.raizesdonordeste.core.domain.payment.PaymentDomain;
import com.uninter.raizesdonordeste.core.domain.payment.PaymentStatus;
import com.uninter.raizesdonordeste.core.domain.payment.PaymentType;
import com.uninter.raizesdonordeste.dataprovider.database.order.OrderEntity;
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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column
    private String gatewayTransactionId;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    public PaymentDomain toDomain() {
        return PaymentDomain.builder()
            .id(id)
            .orderId(order != null ? order.getId() : null)
            .paymentType(paymentType)
            .status(status)
            .amount(amount)
            .gatewayTransactionId(gatewayTransactionId)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .build();
    }

    public static PaymentEntity from(final PaymentDomain domain, final OrderEntity orderEntity) {
        return PaymentEntity.builder()
            .id(domain.getId())
            .order(orderEntity)
            .paymentType(domain.getPaymentType())
            .status(domain.getStatus())
            .amount(domain.getAmount())
            .gatewayTransactionId(domain.getGatewayTransactionId())
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .build();
    }

}
