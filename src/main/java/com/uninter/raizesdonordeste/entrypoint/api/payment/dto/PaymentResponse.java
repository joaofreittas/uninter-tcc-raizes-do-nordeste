package com.uninter.raizesdonordeste.entrypoint.api.payment.dto;

import com.uninter.raizesdonordeste.core.domain.payment.PaymentDomain;
import com.uninter.raizesdonordeste.core.domain.payment.PaymentStatus;
import com.uninter.raizesdonordeste.core.domain.payment.PaymentType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record PaymentResponse(
    Long id,
    Long orderId,
    PaymentType paymentType,
    PaymentStatus status,
    BigDecimal amount,
    String gatewayTransactionId,
    String gatewayProvider,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static PaymentResponse fromDomain(final PaymentDomain domain) {
        return PaymentResponse.builder()
            .id(domain.getId())
            .orderId(domain.getOrderId())
            .paymentType(domain.getPaymentType())
            .status(domain.getStatus())
            .amount(domain.getAmount())
            .gatewayTransactionId(domain.getGatewayTransactionId())
            .gatewayProvider(domain.getGatewayProvider())
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .build();
    }

}
