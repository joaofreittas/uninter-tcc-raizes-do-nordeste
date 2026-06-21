package com.uninter.raizesdonordeste.dataprovider.database.order;

import com.uninter.raizesdonordeste.core.domain.order.OrderDomain;
import com.uninter.raizesdonordeste.core.gateway.OrderGateway;
import com.uninter.raizesdonordeste.dataprovider.database.customer.CustomerRepository;
import com.uninter.raizesdonordeste.dataprovider.database.unit.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderDatabaseGateway implements OrderGateway {

    private final OrderRepository repository;
    private final CustomerRepository customerRepository;
    private final UnitRepository unitRepository;

    @Override
    @Transactional
    public OrderDomain save(final OrderDomain order) {
        var customerEntity = customerRepository.findById(order.getCustomerId()).orElse(null);
        var unitEntity = unitRepository.findById(order.getUnitId()).orElse(null);
        return repository.save(OrderEntity.from(order, customerEntity, unitEntity)).toDomain();
    }

    @Override
    public Optional<OrderDomain> findById(final Long id) {
        return repository.findById(id).map(OrderEntity::toDomain);
    }

    @Override
    public List<OrderDomain> findByCustomerId(final Long customerId) {
        return repository.findByCustomerId(customerId).stream().map(OrderEntity::toDomain).toList();
    }

    @Override
    public List<OrderDomain> findByUnitId(final Long unitId) {
        return repository.findByUnitId(unitId).stream().map(OrderEntity::toDomain).toList();
    }

    @Override
    public List<OrderDomain> findAll() {
        return repository.findAll().stream().map(OrderEntity::toDomain).toList();
    }

    @Override
    @Transactional
    public void delete(final OrderDomain order) {
        repository.deleteById(order.getId());
    }

}
