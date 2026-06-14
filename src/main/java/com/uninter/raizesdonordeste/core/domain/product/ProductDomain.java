package com.uninter.raizesdonordeste.core.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDomain {

    private Long id;
    private String name;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProductDomain create(final String name, final BigDecimal price) {
        return ProductDomain.builder()
            .name(name)
            .price(price)
            .createdAt(LocalDateTime.now())
            .build();
    }

    public ProductDomain update(final String name, final BigDecimal price) {
        return ProductDomain.builder()
            .id(this.id)
            .name(name)
            .price(price)
            .createdAt(this.createdAt)
            .updatedAt(LocalDateTime.now())
            .build();
    }

}
