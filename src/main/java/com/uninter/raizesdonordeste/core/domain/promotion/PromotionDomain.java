package com.uninter.raizesdonordeste.core.domain.promotion;

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
public class PromotionDomain {

    private Long id;
    private Long unitId;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private PromotionReward reward;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PromotionDomain create(final Long unitId, final String title,
                                         final LocalDateTime startDate, final LocalDateTime endDate,
                                         final PromotionReward reward) {
        if (!startDate.isBefore(endDate)) {
            throw new DomainException("startDate deve ser anterior a endDate");
        }
        return PromotionDomain.builder()
            .unitId(unitId)
            .title(title)
            .startDate(startDate)
            .endDate(endDate)
            .reward(reward)
            .createdAt(LocalDateTime.now())
            .build();
    }

    public PromotionDomain update(final String title, final LocalDateTime startDate,
                                  final LocalDateTime endDate, final PromotionReward reward) {
        if (!startDate.isBefore(endDate)) {
            throw new DomainException("startDate deve ser anterior a endDate");
        }
        return PromotionDomain.builder()
            .id(this.id)
            .unitId(this.unitId)
            .title(title)
            .startDate(startDate)
            .endDate(endDate)
            .reward(reward)
            .createdAt(this.createdAt)
            .updatedAt(LocalDateTime.now())
            .build();
    }

}
