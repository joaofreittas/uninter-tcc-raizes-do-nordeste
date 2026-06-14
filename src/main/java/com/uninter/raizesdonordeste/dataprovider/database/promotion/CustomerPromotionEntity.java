package com.uninter.raizesdonordeste.dataprovider.database.promotion;

import com.uninter.raizesdonordeste.core.domain.promotion.CustomerPromotionDomain;
import com.uninter.raizesdonordeste.dataprovider.database.customer.CustomerEntity;
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
@Table(name = "customer_promotions")
public class CustomerPromotionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id", nullable = false)
    private PromotionEntity promotion;

    @Column(nullable = false)
    private String promotionTitle;

    @Column(nullable = false)
    private Boolean active;

    @Column
    private LocalDateTime assignedAt;

    public CustomerPromotionDomain toDomain() {
        return CustomerPromotionDomain.builder()
            .id(id)
            .customerId(customer != null ? customer.getId() : null)
            .promotionId(promotion != null ? promotion.getId() : null)
            .promotionTitle(promotionTitle)
            .active(active)
            .assignedAt(assignedAt)
            .build();
    }

    public static CustomerPromotionEntity from(final CustomerPromotionDomain domain,
                                               final CustomerEntity customerEntity,
                                               final PromotionEntity promotionEntity) {
        return CustomerPromotionEntity.builder()
            .id(domain.getId())
            .customer(customerEntity)
            .promotion(promotionEntity)
            .promotionTitle(domain.getPromotionTitle())
            .active(domain.getActive())
            .assignedAt(domain.getAssignedAt())
            .build();
    }

}
