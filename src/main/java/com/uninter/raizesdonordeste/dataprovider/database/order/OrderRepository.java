package com.uninter.raizesdonordeste.dataprovider.database.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByCustomerId(Long customerId);

    List<OrderEntity> findByUnitId(Long unitId);

}
