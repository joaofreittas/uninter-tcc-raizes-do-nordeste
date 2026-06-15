package com.uninter.raizesdonordeste.entrypoint.api.inventory;

import com.uninter.raizesdonordeste.core.input.inventory.AdjustStockInput;
import com.uninter.raizesdonordeste.core.input.inventory.CreateInventoryItemInput;
import com.uninter.raizesdonordeste.core.input.inventory.UpdateInventoryItemInput;
import com.uninter.raizesdonordeste.core.usecase.inventory.AddStockUseCase;
import com.uninter.raizesdonordeste.core.usecase.inventory.CreateInventoryItemUseCase;
import com.uninter.raizesdonordeste.core.usecase.inventory.FindAllInventoryItemsUseCase;
import com.uninter.raizesdonordeste.core.usecase.inventory.FindInventoryItemByIdUseCase;
import com.uninter.raizesdonordeste.core.usecase.inventory.RemoveStockUseCase;
import com.uninter.raizesdonordeste.core.usecase.inventory.UpdateInventoryItemUseCase;
import com.uninter.raizesdonordeste.entrypoint.api.inventory.dto.AdjustStockRequest;
import com.uninter.raizesdonordeste.entrypoint.api.inventory.dto.CreateInventoryItemRequest;
import com.uninter.raizesdonordeste.entrypoint.api.inventory.dto.InventoryItemResponse;
import com.uninter.raizesdonordeste.entrypoint.api.inventory.dto.UpdateInventoryItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InventoryItemFacade {

    private final CreateInventoryItemUseCase createInventoryItemUseCase;
    private final FindAllInventoryItemsUseCase findAllInventoryItemsUseCase;
    private final FindInventoryItemByIdUseCase findInventoryItemByIdUseCase;
    private final UpdateInventoryItemUseCase updateInventoryItemUseCase;
    private final AddStockUseCase addStockUseCase;
    private final RemoveStockUseCase removeStockUseCase;

    public InventoryItemResponse create(final CreateInventoryItemRequest request) {
        var domain = createInventoryItemUseCase.execute(new CreateInventoryItemInput(
            request.unitId(), request.productId(), request.quantity(), request.minimumQuantity()));
        return InventoryItemResponse.fromDomain(domain);
    }

    public List<InventoryItemResponse> findAll(final Optional<Long> unitId, final Optional<Long> productId) {
        return findAllInventoryItemsUseCase.execute(unitId, productId)
            .stream().map(InventoryItemResponse::fromDomain).toList();
    }

    public InventoryItemResponse findById(final Long id) {
        return InventoryItemResponse.fromDomain(findInventoryItemByIdUseCase.execute(id));
    }

    public InventoryItemResponse update(final Long id, final UpdateInventoryItemRequest request) {
        return InventoryItemResponse.fromDomain(updateInventoryItemUseCase.execute(
            new UpdateInventoryItemInput(id, request.quantity(), request.minimumQuantity())));
    }

    public InventoryItemResponse addStock(final Long id, final AdjustStockRequest request) {
        return InventoryItemResponse.fromDomain(
            addStockUseCase.execute(new AdjustStockInput(id, request.amount())));
    }

    public InventoryItemResponse removeStock(final Long id, final AdjustStockRequest request) {
        return InventoryItemResponse.fromDomain(
            removeStockUseCase.execute(new AdjustStockInput(id, request.amount())));
    }

}
