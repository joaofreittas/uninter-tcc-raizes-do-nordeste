package com.uninter.raizesdonordeste.dataprovider.database.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryItemRepository extends JpaRepository<InventoryItemEntity, Long> {

    List<InventoryItemEntity> findByUnitId(Long unitId);

    List<InventoryItemEntity> findByProductId(Long productId);

}
