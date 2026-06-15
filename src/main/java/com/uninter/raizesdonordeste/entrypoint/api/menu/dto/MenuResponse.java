package com.uninter.raizesdonordeste.entrypoint.api.menu.dto;

import com.uninter.raizesdonordeste.core.domain.menu.MenuDomain;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record MenuResponse(
    Long id,
    Long unitId,
    List<MenuProductItem> products,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    @Builder
    public record MenuProductItem(Long productId, String name, BigDecimal price) {}

    public static MenuResponse fromDomain(final MenuDomain domain) {
        var products = domain.getProducts() == null ? List.<MenuProductItem>of()
            : domain.getProducts().stream()
            .map(p -> MenuProductItem.builder()
                .productId(p.getProductId())
                .name(p.getName())
                .price(p.getPrice())
                .build())
            .toList();

        return MenuResponse.builder()
            .id(domain.getId())
            .unitId(domain.getUnitId())
            .products(products)
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .build();
    }

}
