package com.uninter.raizesdonordeste.dataprovider.database.promotion;

import com.uninter.raizesdonordeste.core.domain.promotion.PromotionDomain;
import com.uninter.raizesdonordeste.core.domain.promotion.PromotionReward;
import com.uninter.raizesdonordeste.dataprovider.database.unit.UnitEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "promotions")
public class PromotionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private UnitEntity unit;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PromotionReward reward;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    public PromotionDomain toDomain() {
        return PromotionDomain.builder()
            .id(id)
            .unitId(unit != null ? unit.getId() : null)
            .title(title)
            .startDate(startDate)
            .endDate(endDate)
            .reward(reward)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .build();
    }

    public static PromotionEntity from(final PromotionDomain domain, final UnitEntity unitEntity) {
        return PromotionEntity.builder()
            .id(domain.getId())
            .unit(unitEntity)
            .title(domain.getTitle())
            .startDate(domain.getStartDate())
            .endDate(domain.getEndDate())
            .reward(domain.getReward())
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .build();
    }

}
