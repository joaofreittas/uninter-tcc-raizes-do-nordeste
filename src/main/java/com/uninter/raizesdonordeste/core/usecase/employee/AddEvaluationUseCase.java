package com.uninter.raizesdonordeste.core.usecase.employee;

import com.uninter.raizesdonordeste.core.domain.audit.AuditAction;
import com.uninter.raizesdonordeste.core.domain.audit.Auditable;
import com.uninter.raizesdonordeste.core.domain.employee.EvaluationDomain;
import com.uninter.raizesdonordeste.core.gateway.EvaluationGateway;
import com.uninter.raizesdonordeste.core.input.employee.AddEvaluationInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddEvaluationUseCase {

    private final FindEmployeeByIdUseCase findEmployeeByIdUseCase;
    private final EvaluationGateway evaluationGateway;

    @Auditable(action = AuditAction.CREATE, entity = "Evaluation")
    public EvaluationDomain execute(final AddEvaluationInput input) {
        findEmployeeByIdUseCase.execute(input.employeeId());
        return evaluationGateway.save(
            EvaluationDomain.create(input.employeeId(), input.rating(), input.comment()));
    }

}
