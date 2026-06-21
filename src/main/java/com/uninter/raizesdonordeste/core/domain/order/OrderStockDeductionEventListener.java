package com.uninter.raizesdonordeste.core.domain.order;

import com.uninter.raizesdonordeste.core.usecase.order.DeductOrderStockUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStockDeductionEventListener {

    private final DeductOrderStockUseCase deductOrderStockUseCase;

    @Async
    @EventListener
    public void onPaymentApproved(final OrderPaymentApprovedEvent event) {
        deductOrderStockUseCase.execute(event.orderId());
    }

}
