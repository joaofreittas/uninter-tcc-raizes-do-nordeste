package com.uninter.raizesdonordeste.dataprovider.database.inventory;

import com.uninter.raizesdonordeste.core.domain.inventory.InventoryItemDomain;
import com.uninter.raizesdonordeste.dataprovider.database.product.ProductEntity;
import com.uninter.raizesdonordeste.dataprovider.database.unit.UnitEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inventory_items")
public class InventoryItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private UnitEntity unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer minimumQuantity;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    public InventoryItemDomain toDomain() {
        return InventoryItemDomain.builder()
            .id(id)
            .unitId(unit != null ? unit.getId() : null)
            .productId(product != null ? product.getId() : null)
            .quantity(quantity)
            .minimumQuantity(minimumQuantity)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .build();
    }

    public static InventoryItemEntity from(final InventoryItemDomain domain,
                                           final UnitEntity unitEntity,
                                           final ProductEntity productEntity) {
        return InventoryItemEntity.builder()
            .id(domain.getId())
            .unit(unitEntity)
            .product(productEntity)
            .quantity(domain.getQuantity())
            .minimumQuantity(domain.getMinimumQuantity())
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .build();
    }

}
