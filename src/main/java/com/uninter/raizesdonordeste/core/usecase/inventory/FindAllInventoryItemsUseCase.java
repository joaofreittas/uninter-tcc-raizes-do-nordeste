package com.uninter.raizesdonordeste.core.usecase.inventory;

import com.uninter.raizesdonordeste.core.domain.inventory.InventoryItemDomain;
import com.uninter.raizesdonordeste.core.gateway.InventoryItemGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindAllInventoryItemsUseCase {

    private final InventoryItemGateway inventoryItemGateway;

    public List<InventoryItemDomain> execute(final Optional<Long> unitId, final Optional<Long> productId) {
        return unitId.map(inventoryItemGateway::findByUnitId)
            .or(() -> productId.map(inventoryItemGateway::findByProductId))
            .orElseGet(inventoryItemGateway::findAll);
    }

}
