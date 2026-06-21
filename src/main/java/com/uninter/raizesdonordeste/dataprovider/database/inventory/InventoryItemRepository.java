package com.uninter.raizesdonordeste.dataprovider.database.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryItemRepository extends JpaRepository<InventoryItemEntity, Long> {

    List<InventoryItemEntity> findByUnitId(Long unitId);

    List<InventoryItemEntity> findByProductId(Long productId);

    Optional<InventoryItemEntity> findByUnitIdAndProductId(Long unitId, Long productId);

}
