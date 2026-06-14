package com.uninter.raizesdonordeste.core.domain.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDomain {

    private Long id;
    private Long unitId;
    private String name;
    private String address;
    private String document;
    private LocalDate birthDate;
    private EmployeeType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<EvaluationDomain> evaluations;

    public static EmployeeDomain create(final Long unitId, final String name, final String address,
                                        final String document, final LocalDate birthDate,
                                        final EmployeeType type) {
        return EmployeeDomain.builder()
            .unitId(unitId)
            .name(name)
            .address(address)
            .document(document)
            .birthDate(birthDate)
            .type(type)
            .createdAt(LocalDateTime.now())
            .build();
    }

    public EmployeeDomain update(final String name, final String address,
                                 final LocalDate birthDate, final EmployeeType type) {
        return EmployeeDomain.builder()
            .id(this.id)
            .unitId(this.unitId)
            .name(name)
            .address(address)
            .document(this.document)
            .birthDate(birthDate)
            .type(type)
            .evaluations(this.evaluations)
            .createdAt(this.createdAt)
            .updatedAt(LocalDateTime.now())
            .build();
    }

}
