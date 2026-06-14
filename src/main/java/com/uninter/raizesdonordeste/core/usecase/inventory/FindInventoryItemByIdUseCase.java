package com.uninter.raizesdonordeste.core.usecase.inventory;

import com.uninter.raizesdonordeste.core.domain.inventory.InventoryItemDomain;
import com.uninter.raizesdonordeste.core.exception.ResourceNotFoundException;
import com.uninter.raizesdonordeste.core.gateway.InventoryItemGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindInventoryItemByIdUseCase {

    private final InventoryItemGateway inventoryItemGateway;

    public InventoryItemDomain execute(final Long id) {
        return inventoryItemGateway.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("InventoryItem not found: " + id));
    }

}
