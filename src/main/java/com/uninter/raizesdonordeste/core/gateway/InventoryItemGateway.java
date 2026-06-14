package com.uninter.raizesdonordeste.core.gateway;

import com.uninter.raizesdonordeste.core.domain.inventory.InventoryItemDomain;

import java.util.List;
import java.util.Optional;

public interface InventoryItemGateway {

    InventoryItemDomain save(InventoryItemDomain item);

    Optional<InventoryItemDomain> findById(Long id);

    List<InventoryItemDomain> findAll();

    List<InventoryItemDomain> findByUnitId(Long unitId);

    List<InventoryItemDomain> findByProductId(Long productId);

}
