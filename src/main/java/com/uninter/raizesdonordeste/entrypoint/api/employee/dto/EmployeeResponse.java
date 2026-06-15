package com.uninter.raizesdonordeste.entrypoint.api.employee.dto;

import com.uninter.raizesdonordeste.core.domain.employee.EmployeeDomain;
import com.uninter.raizesdonordeste.core.domain.employee.EmployeeType;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record EmployeeResponse(
    Long id,
    Long unitId,
    String name,
    String address,
    String document,
    LocalDate birthDate,
    EmployeeType type,
    List<EvaluationResponse> evaluations,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static EmployeeResponse fromDomain(final EmployeeDomain domain) {
        var evaluations = domain.getEvaluations() == null ? List.<EvaluationResponse>of()
            : domain.getEvaluations().stream().map(EvaluationResponse::fromDomain).toList();

        return EmployeeResponse.builder()
            .id(domain.getId())
            .unitId(domain.getUnitId())
            .name(domain.getName())
            .address(domain.getAddress())
            .document(domain.getDocument())
            .birthDate(domain.getBirthDate())
            .type(domain.getType())
            .evaluations(evaluations)
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .build();
    }

}
