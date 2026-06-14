package com.uninter.raizesdonordeste.core.domain.employee;

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
public class EvaluationDomain {

    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 5;

    private Long id;
    private Long employeeId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static EvaluationDomain create(final Long employeeId, final Integer rating, final String comment) {
        if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new DomainException("Rating must be between " + MIN_RATING + " and " + MAX_RATING);
        }
        return EvaluationDomain.builder()
            .employeeId(employeeId)
            .rating(rating)
            .comment(comment)
            .createdAt(LocalDateTime.now())
            .build();
    }

}
