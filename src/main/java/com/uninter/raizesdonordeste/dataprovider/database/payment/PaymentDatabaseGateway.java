package com.uninter.raizesdonordeste.dataprovider.database.payment;

import com.uninter.raizesdonordeste.core.domain.payment.PaymentDomain;
import com.uninter.raizesdonordeste.core.gateway.PaymentGateway;
import com.uninter.raizesdonordeste.dataprovider.database.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentDatabaseGateway implements PaymentGateway {

    private final PaymentRepository repository;
    private final OrderRepository orderRepository;

    @Override
    public PaymentDomain save(final PaymentDomain payment) {
        var orderEntity = orderRepository.findById(payment.getOrderId()).orElse(null);
        return repository.save(PaymentEntity.from(payment, orderEntity)).toDomain();
    }

    @Override
    public Optional<PaymentDomain> findById(final Long id) {
        return repository.findById(id).map(PaymentEntity::toDomain);
    }

    @Override
    public List<PaymentDomain> findByOrderId(final Long orderId) {
        return repository.findByOrderId(orderId).stream().map(PaymentEntity::toDomain).toList();
    }

    @Override
    public List<PaymentDomain> findAll() {
        return repository.findAll().stream().map(PaymentEntity::toDomain).toList();
    }

    @Override
    public void delete(final PaymentDomain payment) {
        repository.deleteById(payment.getId());
    }

}
