package com.uninter.raizesdonordeste.core.domain.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDomain {

    private Long id;
    private Long unitId;
    private List<MenuProductItemDomain> products;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MenuDomain create(final Long unitId) {
        return MenuDomain.builder()
            .unitId(unitId)
            .createdAt(LocalDateTime.now())
            .build();
    }

    public MenuDomain touch() {
        return MenuDomain.builder()
            .id(this.id)
            .unitId(this.unitId)
            .products(this.products)
            .createdAt(this.createdAt)
            .updatedAt(LocalDateTime.now())
            .build();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MenuProductItemDomain {
        private Long productId;
        private String name;
        private BigDecimal price;
    }

}
