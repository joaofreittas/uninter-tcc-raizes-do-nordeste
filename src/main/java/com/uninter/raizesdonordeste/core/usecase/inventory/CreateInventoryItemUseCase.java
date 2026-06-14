package com.uninter.raizesdonordeste.core.usecase.inventory;

import com.uninter.raizesdonordeste.core.domain.audit.AuditAction;
import com.uninter.raizesdonordeste.core.domain.audit.Auditable;
import com.uninter.raizesdonordeste.core.domain.inventory.InventoryItemDomain;
import com.uninter.raizesdonordeste.core.exception.DomainException;
import com.uninter.raizesdonordeste.core.gateway.InventoryItemGateway;
import com.uninter.raizesdonordeste.core.gateway.ProductGateway;
import com.uninter.raizesdonordeste.core.gateway.UnitGateway;
import com.uninter.raizesdonordeste.core.input.inventory.CreateInventoryItemInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateInventoryItemUseCase {

    private final InventoryItemGateway inventoryItemGateway;
    private final UnitGateway unitGateway;
    private final ProductGateway productGateway;

    @Auditable(action = AuditAction.CREATE)
    public InventoryItemDomain execute(final CreateInventoryItemInput input) {
        unitGateway.findById(input.unitId())
            .orElseThrow(() -> new DomainException("Unit not found: " + input.unitId()));
        productGateway.findById(input.productId())
            .orElseThrow(() -> new DomainException("Product not found: " + input.productId()));

        return inventoryItemGateway.save(
            InventoryItemDomain.create(input.unitId(), input.productId(),
                input.quantity(), input.minimumQuantity()));
    }

}
