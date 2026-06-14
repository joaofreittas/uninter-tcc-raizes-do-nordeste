package com.uninter.raizesdonordeste.dataprovider.database.employee;

import com.uninter.raizesdonordeste.core.domain.employee.EvaluationDomain;
import com.uninter.raizesdonordeste.core.gateway.EvaluationGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EvaluationDatabaseGateway implements EvaluationGateway {

    private final EvaluationRepository evaluationRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public EvaluationDomain save(final EvaluationDomain evaluation) {
        var employeeEntity = employeeRepository.findById(evaluation.getEmployeeId()).orElse(null);
        return evaluationRepository.save(EvaluationEntity.from(evaluation, employeeEntity)).toDomain();
    }

    @Override
    public List<EvaluationDomain> findByEmployeeId(final Long employeeId) {
        return evaluationRepository.findByEmployeeId(employeeId).stream()
            .map(EvaluationEntity::toDomain).toList();
    }

}
