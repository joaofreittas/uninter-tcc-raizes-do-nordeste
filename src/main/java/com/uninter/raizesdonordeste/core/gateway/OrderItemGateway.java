package com.uninter.raizesdonordeste.core.gateway;

import com.uninter.raizesdonordeste.core.domain.order.OrderItemDomain;

import java.util.List;
import java.util.Optional;

public interface OrderItemGateway {

    OrderItemDomain save(OrderItemDomain item);

    Optional<OrderItemDomain> findById(Long id);

    List<OrderItemDomain> findByOrderId(Long orderId);

    void delete(OrderItemDomain item);

}
