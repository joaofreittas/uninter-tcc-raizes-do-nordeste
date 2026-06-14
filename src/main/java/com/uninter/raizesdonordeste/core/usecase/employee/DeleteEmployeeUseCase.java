package com.uninter.raizesdonordeste.core.usecase.employee;

import com.uninter.raizesdonordeste.core.domain.audit.AuditAction;
import com.uninter.raizesdonordeste.core.domain.audit.Auditable;
import com.uninter.raizesdonordeste.core.gateway.EmployeeGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteEmployeeUseCase {

    private final FindEmployeeByIdUseCase findEmployeeByIdUseCase;
    private final EmployeeGateway employeeGateway;

    @Auditable(action = AuditAction.DELETE)
    public void execute(final Long id) {
        var employee = findEmployeeByIdUseCase.execute(id);
        employeeGateway.delete(employee);
    }

}
