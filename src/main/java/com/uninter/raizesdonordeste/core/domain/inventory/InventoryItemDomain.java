package com.uninter.raizesdonordeste.core.domain.inventory;

import com.uninter.raizesdonordeste.core.exception.DomainException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryItemDomain {

    private Long id;
    private Long unitId;
    private Long productId;
    private Integer quantity;
    private Integer minimumQuantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static InventoryItemDomain create(final Long unitId, final Long productId,
                                             final Integer quantity, final Integer minimumQuantity) {
        return InventoryItemDomain.builder()
            .unitId(unitId)
            .productId(productId)
            .quantity(quantity)
            .minimumQuantity(minimumQuantity)
            .createdAt(LocalDateTime.now())
            .build();
    }

    public InventoryItemDomain update(final Integer quantity, final Integer minimumQuantity) {
        return InventoryItemDomain.builder()
            .id(this.id)
            .unitId(this.unitId)
            .productId(this.productId)
            .quantity(quantity)
            .minimumQuantity(minimumQuantity)
            .createdAt(this.createdAt)
            .updatedAt(LocalDateTime.now())
            .build();
    }

    public InventoryItemDomain addStock(final Integer amount) {
        return InventoryItemDomain.builder()
            .id(this.id)
            .unitId(this.unitId)
            .productId(this.productId)
            .quantity(this.quantity + amount)
            .minimumQuantity(this.minimumQuantity)
            .createdAt(this.createdAt)
            .updatedAt(LocalDateTime.now())
            .build();
    }

    public InventoryItemDomain removeStock(final Integer amount) {
        var newQuantity = this.quantity - amount;
        if (newQuantity < 0) {
            throw new DomainException(
                "Insufficient stock. Current: " + this.quantity + ", requested: " + amount);
        }
        return InventoryItemDomain.builder()
            .id(this.id)
            .unitId(this.unitId)
            .productId(this.productId)
            .quantity(newQuantity)
            .minimumQuantity(this.minimumQuantity)
            .createdAt(this.createdAt)
            .updatedAt(LocalDateTime.now())
            .build();
    }

}
