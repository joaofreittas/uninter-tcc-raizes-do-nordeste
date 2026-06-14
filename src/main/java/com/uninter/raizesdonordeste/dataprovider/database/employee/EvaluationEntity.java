package com.uninter.raizesdonordeste.dataprovider.database.employee;

import com.uninter.raizesdonordeste.core.domain.employee.EvaluationDomain;
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
@Table(name = "evaluations")
public class EvaluationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    @Column(nullable = false)
    private Integer rating;

    @Column
    private String comment;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    public EvaluationDomain toDomain() {
        return EvaluationDomain.builder()
            .id(id)
            .employeeId(employee != null ? employee.getId() : null)
            .rating(rating)
            .comment(comment)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .build();
    }

    public static EvaluationEntity from(final EvaluationDomain domain, final EmployeeEntity employeeEntity) {
        return EvaluationEntity.builder()
            .id(domain.getId())
            .employee(employeeEntity)
            .rating(domain.getRating())
            .comment(domain.getComment())
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .build();
    }

}
