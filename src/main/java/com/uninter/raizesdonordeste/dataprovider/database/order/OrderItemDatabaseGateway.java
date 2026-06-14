package com.uninter.raizesdonordeste.dataprovider.database.order;

import com.uninter.raizesdonordeste.core.domain.order.OrderItemDomain;
import com.uninter.raizesdonordeste.core.gateway.OrderItemGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderItemDatabaseGateway implements OrderItemGateway {

    private final OrderItemRepository repository;
    private final OrderRepository orderRepository;

    @Override
    public OrderItemDomain save(final OrderItemDomain item) {
        var orderEntity = orderRepository.findById(item.getOrderId()).orElse(null);
        return repository.save(OrderItemEntity.from(item, orderEntity)).toDomain();
    }

    @Override
    public Optional<OrderItemDomain> findById(final Long id) {
        return repository.findById(id).map(OrderItemEntity::toDomain);
    }

    @Override
    public List<OrderItemDomain> findByOrderId(final Long orderId) {
        return repository.findByOrderId(orderId).stream().map(OrderItemEntity::toDomain).toList();
    }

    @Override
    public void delete(final OrderItemDomain item) {
        repository.deleteById(item.getId());
    }

}
