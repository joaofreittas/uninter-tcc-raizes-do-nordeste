package com.uninter.raizesdonordeste.core.domain.promotion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPromotionDomain {

    private Long id;
    private Long customerId;
    private Long promotionId;
    private String promotionTitle;
    private Boolean active;
    private LocalDateTime assignedAt;

}
