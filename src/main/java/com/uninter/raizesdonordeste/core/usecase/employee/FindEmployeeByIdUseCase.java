package com.uninter.raizesdonordeste.core.usecase.employee;

import com.uninter.raizesdonordeste.core.domain.employee.EmployeeDomain;
import com.uninter.raizesdonordeste.core.exception.ResourceNotFoundException;
import com.uninter.raizesdonordeste.core.gateway.EmployeeGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindEmployeeByIdUseCase {

    private final EmployeeGateway employeeGateway;

    public EmployeeDomain execute(final Long id) {
        return employeeGateway.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
    }

}
