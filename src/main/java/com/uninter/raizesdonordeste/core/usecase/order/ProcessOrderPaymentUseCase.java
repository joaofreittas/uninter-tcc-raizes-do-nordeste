package com.uninter.raizesdonordeste.core.usecase.order;

import com.uninter.raizesdonordeste.core.domain.payment.PaymentDomain;
import com.uninter.raizesdonordeste.core.domain.payment.PaymentType;
import com.uninter.raizesdonordeste.core.exception.ResourceNotFoundException;
import com.uninter.raizesdonordeste.core.gateway.OrderGateway;
import com.uninter.raizesdonordeste.core.gateway.PaymentGateway;
import com.uninter.raizesdonordeste.core.gateway.PaymentProviderGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessOrderPaymentUseCase {

    private final OrderGateway orderGateway;
    private final PaymentGateway paymentGateway;
    private final PaymentProviderGateway paymentProviderGateway;

    public void execute(final Long orderId, final PaymentType paymentType) {
        var order = orderGateway.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));

        var pendingPayment = paymentGateway.save(
            PaymentDomain.create(orderId, paymentType, order.getTotalAmount()));

        try {
            var result = paymentProviderGateway.processPayment(order.getTotalAmount());
            pendingPayment.approve(result.gatewayTransactionId());
            paymentGateway.save(pendingPayment);
            order.confirm();

            orderGateway.save(order);
        } catch (Exception ex) {
            log.error("Payment processing failed for order {}: {}", orderId, ex.getMessage());
            pendingPayment.decline();
            paymentGateway.save(pendingPayment);
            order.cancel();

            orderGateway.save(order);
        }
    }

}
