package com.uninter.raizesdonordeste.entrypoint.api.employee.dto;

import com.uninter.raizesdonordeste.core.domain.employee.EvaluationDomain;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record EvaluationResponse(
    Long id,
    Integer rating,
    String comment,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static EvaluationResponse fromDomain(final EvaluationDomain domain) {
        return EvaluationResponse.builder()
            .id(domain.getId())
            .rating(domain.getRating())
            .comment(domain.getComment())
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .build();
    }

}
