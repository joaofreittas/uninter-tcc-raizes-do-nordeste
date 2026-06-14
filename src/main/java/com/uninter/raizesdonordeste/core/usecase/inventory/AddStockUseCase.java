package com.uninter.raizesdonordeste.core.usecase.inventory;

import com.uninter.raizesdonordeste.core.domain.audit.AuditAction;
import com.uninter.raizesdonordeste.core.domain.audit.Auditable;
import com.uninter.raizesdonordeste.core.domain.inventory.InventoryItemDomain;
import com.uninter.raizesdonordeste.core.gateway.InventoryItemGateway;
import com.uninter.raizesdonordeste.core.input.inventory.AdjustStockInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddStockUseCase {

    private final FindInventoryItemByIdUseCase findInventoryItemByIdUseCase;
    private final InventoryItemGateway inventoryItemGateway;

    @Auditable(action = AuditAction.UPDATE, entity = "InventoryItem.addStock")
    public InventoryItemDomain execute(final AdjustStockInput input) {
        var existing = findInventoryItemByIdUseCase.execute(input.id());
        return inventoryItemGateway.save(existing.addStock(input.amount()));
    }

}
