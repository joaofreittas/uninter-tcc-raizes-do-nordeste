package com.uninter.raizesdonordeste.dataprovider.database.menu;

import com.uninter.raizesdonordeste.core.domain.menu.MenuDomain;
import com.uninter.raizesdonordeste.dataprovider.database.unit.UnitEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menus", uniqueConstraints = @UniqueConstraint(name = "uk_menus_unit_id", columnNames = "unit_id"))
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private UnitEntity unit;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "menu")
    private List<MenuProductEntity> menuProducts;

    public MenuDomain toDomain() {
        var products = menuProducts == null ? List.<MenuDomain.MenuProductItemDomain>of()
            : menuProducts.stream()
            .map(mp -> MenuDomain.MenuProductItemDomain.builder()
                .productId(mp.getProduct().getId())
                .name(mp.getProduct().getName())
                .price(mp.getProduct().getPrice())
                .build())
            .toList();
        return MenuDomain.builder()
            .id(id)
            .unitId(unit != null ? unit.getId() : null)
            .products(products)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .build();
    }

    public static MenuEntity from(final MenuDomain domain, final UnitEntity unitEntity) {
        return MenuEntity.builder()
            .id(domain.getId())
            .unit(unitEntity)
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .build();
    }

}
