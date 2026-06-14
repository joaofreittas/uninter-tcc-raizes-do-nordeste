package com.uninter.raizesdonordeste.core.usecase.inventory;

import com.uninter.raizesdonordeste.core.domain.audit.AuditAction;
import com.uninter.raizesdonordeste.core.domain.audit.Auditable;
import com.uninter.raizesdonordeste.core.domain.inventory.InventoryItemDomain;
import com.uninter.raizesdonordeste.core.gateway.InventoryItemGateway;
import com.uninter.raizesdonordeste.core.input.inventory.UpdateInventoryItemInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateInventoryItemUseCase {

    private final FindInventoryItemByIdUseCase findInventoryItemByIdUseCase;
    private final InventoryItemGateway inventoryItemGateway;

    @Auditable(action = AuditAction.UPDATE)
    public InventoryItemDomain execute(final UpdateInventoryItemInput input) {
        var existing = findInventoryItemByIdUseCase.execute(input.id());
        return inventoryItemGateway.save(existing.update(input.quantity(), input.minimumQuantity()));
    }

}
