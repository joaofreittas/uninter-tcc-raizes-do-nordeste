package com.uninter.raizesdonordeste.entrypoint.api.promotion.dto;

import com.uninter.raizesdonordeste.core.domain.promotion.PromotionReward;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UpdatePromotionRequest(
    @NotBlank String title,
    @NotNull LocalDateTime startDate,
    @NotNull LocalDateTime endDate,
    @NotNull PromotionReward reward
) {

}
