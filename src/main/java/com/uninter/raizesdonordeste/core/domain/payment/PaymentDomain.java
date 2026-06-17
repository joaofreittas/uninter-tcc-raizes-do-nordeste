package com.uninter.raizesdonordeste.core.domain.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDomain {

    private Long id;
    private Long orderId;
    private PaymentType paymentType;
    private PaymentStatus status;
    private BigDecimal amount;
    private String gatewayTransactionId;
    private String gatewayProvider;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PaymentDomain create(final Long orderId, final PaymentType paymentType, final BigDecimal amount) {
        return PaymentDomain.builder()
            .orderId(orderId)
            .paymentType(paymentType)
            .status(PaymentStatus.PENDING)
            .amount(amount)
            .createdAt(LocalDateTime.now())
            .build();
    }

    public void approve(final String transactionId) {
        this.status = PaymentStatus.APPROVED;
        this.gatewayTransactionId = transactionId;
        this.updatedAt = LocalDateTime.now();
    }

    public void decline() {
        this.status = PaymentStatus.DECLINED;
        this.updatedAt = LocalDateTime.now();
    }

}
