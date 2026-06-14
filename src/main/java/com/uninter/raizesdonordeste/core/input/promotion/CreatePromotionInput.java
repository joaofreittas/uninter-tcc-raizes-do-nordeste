package com.uninter.raizesdonordeste.core.input.promotion;

import com.uninter.raizesdonordeste.core.domain.promotion.PromotionReward;

import java.time.LocalDateTime;

public record CreatePromotionInput(
    Long unitId,
    String title,
    LocalDateTime startDate,
    LocalDateTime endDate,
    PromotionReward reward
) {

}
