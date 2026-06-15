package com.uninter.raizesdonordeste.entrypoint.api.inventory.dto;

import com.uninter.raizesdonordeste.core.domain.inventory.InventoryItemDomain;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record InventoryItemResponse(
    Long id,
    Long unitId,
    Long productId,
    Integer quantity,
    Integer minimumQuantity,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static InventoryItemResponse fromDomain(final InventoryItemDomain domain) {
        return InventoryItemResponse.builder()
            .id(domain.getId())
            .unitId(domain.getUnitId())
            .productId(domain.getProductId())
            .quantity(domain.getQuantity())
            .minimumQuantity(domain.getMinimumQuantity())
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .build();
    }

}
