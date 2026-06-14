package com.uninter.raizesdonordeste.core.gateway;

import com.uninter.raizesdonordeste.core.domain.order.OrderDomain;

import java.util.List;
import java.util.Optional;

public interface OrderGateway {

    OrderDomain save(OrderDomain order);

    Optional<OrderDomain> findById(Long id);

    List<OrderDomain> findByCustomerId(Long customerId);

    List<OrderDomain> findByUnitId(Long unitId);

    List<OrderDomain> findAll();

    void delete(OrderDomain order);

}
