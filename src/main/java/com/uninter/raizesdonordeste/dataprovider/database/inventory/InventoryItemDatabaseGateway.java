package com.uninter.raizesdonordeste.dataprovider.database.inventory;

import com.uninter.raizesdonordeste.core.domain.inventory.InventoryItemDomain;
import com.uninter.raizesdonordeste.core.gateway.InventoryItemGateway;
import com.uninter.raizesdonordeste.dataprovider.database.product.ProductRepository;
import com.uninter.raizesdonordeste.dataprovider.database.unit.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InventoryItemDatabaseGateway implements InventoryItemGateway {

    private final InventoryItemRepository repository;
    private final UnitRepository unitRepository;
    private final ProductRepository productRepository;

    @Override
    public InventoryItemDomain save(final InventoryItemDomain item) {
        var unitEntity = unitRepository.findById(item.getUnitId()).orElse(null);
        var productEntity = productRepository.findById(item.getProductId()).orElse(null);
        return repository.save(InventoryItemEntity.from(item, unitEntity, productEntity)).toDomain();
    }

    @Override
    public Optional<InventoryItemDomain> findById(final Long id) {
        return repository.findById(id).map(InventoryItemEntity::toDomain);
    }

    @Override
    public List<InventoryItemDomain> findAll() {
        return repository.findAll().stream().map(InventoryItemEntity::toDomain).toList();
    }

    @Override
    public List<InventoryItemDomain> findByUnitId(final Long unitId) {
        return repository.findByUnitId(unitId).stream().map(InventoryItemEntity::toDomain).toList();
    }

    @Override
    public List<InventoryItemDomain> findByProductId(final Long productId) {
        return repository.findByProductId(productId).stream().map(InventoryItemEntity::toDomain).toList();
    }

}
