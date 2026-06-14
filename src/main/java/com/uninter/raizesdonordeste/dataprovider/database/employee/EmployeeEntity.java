package com.uninter.raizesdonordeste.dataprovider.database.employee;

import com.uninter.raizesdonordeste.core.domain.employee.EmployeeDomain;
import com.uninter.raizesdonordeste.core.domain.employee.EmployeeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private UnitEntity unit;

    @Column(nullable = false)
    private String name;

    @Column
    private String address;

    @Column(nullable = false, unique = true)
    private String document;

    @Column
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmployeeType type;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "employee")
    private List<EvaluationEntity> evaluations;

    public EmployeeDomain toDomain() {
        var evalDomains = evaluations == null ? List.<com.uninter.raizesdonordeste.core.domain.employee.EvaluationDomain>of()
            : evaluations.stream().map(EvaluationEntity::toDomain).toList();
        return EmployeeDomain.builder()
            .id(id)
            .unitId(unit != null ? unit.getId() : null)
            .name(name)
            .address(address)
            .document(document)
            .birthDate(birthDate)
            .type(type)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .evaluations(evalDomains)
            .build();
    }

    public static EmployeeEntity from(final EmployeeDomain domain, final UnitEntity unitEntity) {
        return EmployeeEntity.builder()
            .id(domain.getId())
            .unit(unitEntity)
            .name(domain.getName())
            .address(domain.getAddress())
            .document(domain.getDocument())
            .birthDate(domain.getBirthDate())
            .type(domain.getType())
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .build();
    }

}
