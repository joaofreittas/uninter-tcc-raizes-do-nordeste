package com.uninter.raizesdonordeste.dataprovider.database.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    List<PaymentEntity> findByOrderId(Long orderId);

}
