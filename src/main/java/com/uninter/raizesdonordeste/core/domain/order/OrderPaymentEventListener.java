package com.uninter.raizesdonordeste.core.domain.order;

import com.uninter.raizesdonordeste.core.usecase.order.ProcessOrderPaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderPaymentEventListener {

    private final ProcessOrderPaymentUseCase processOrderPaymentUseCase;

    @Async
    @EventListener
    public void onOrderCreated(final OrderCreatedEvent event) {
        processOrderPaymentUseCase.execute(event.orderId(), event.paymentType());
    }

}
