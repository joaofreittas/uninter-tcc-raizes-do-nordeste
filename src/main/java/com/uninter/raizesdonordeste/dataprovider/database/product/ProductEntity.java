package com.uninter.raizesdonordeste.dataprovider.database.product;

import com.uninter.raizesdonordeste.core.domain.product.ProductDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    public ProductDomain toDomain() {
        return ProductDomain.builder()
            .id(id)
            .name(name)
            .price(price)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .build();
    }

    public static ProductEntity from(final ProductDomain domain) {
        return ProductEntity.builder()
            .id(domain.getId())
            .name(domain.getName())
            .price(domain.getPrice())
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .build();
    }

}
