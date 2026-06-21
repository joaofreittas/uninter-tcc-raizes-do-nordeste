package com.uninter.raizesdonordeste.core.usecase.order;

import com.uninter.raizesdonordeste.core.domain.order.OrderDomain;
import com.uninter.raizesdonordeste.core.gateway.OrderGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindOrdersByUnitUseCase {

    private final OrderGateway orderGateway;

    public List<OrderDomain> execute(final Long unitId) {
        return orderGateway.findByUnitId(unitId);
    }

}
