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

}
