package com.uninter.raizesdonordeste.core.usecase.employee;

import com.uninter.raizesdonordeste.core.domain.audit.AuditAction;
import com.uninter.raizesdonordeste.core.domain.audit.Auditable;
import com.uninter.raizesdonordeste.core.domain.employee.EmployeeDomain;
import com.uninter.raizesdonordeste.core.gateway.EmployeeGateway;
import com.uninter.raizesdonordeste.core.input.employee.UpdateEmployeeInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateEmployeeUseCase {

    private final FindEmployeeByIdUseCase findEmployeeByIdUseCase;
    private final EmployeeGateway employeeGateway;

    @Auditable(action = AuditAction.UPDATE)
    public EmployeeDomain execute(final UpdateEmployeeInput input) {
        var existing = findEmployeeByIdUseCase.execute(input.id());
        return employeeGateway.save(
            existing.update(input.name(), input.address(), input.birthDate(), input.type()));
    }

}
