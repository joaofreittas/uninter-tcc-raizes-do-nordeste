package com.uninter.raizesdonordeste.core.usecase.employee;

import com.uninter.raizesdonordeste.core.domain.employee.EmployeeDomain;
import com.uninter.raizesdonordeste.core.gateway.EmployeeGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllEmployeesUseCase {

    private final EmployeeGateway employeeGateway;

    public List<EmployeeDomain> execute() {
        return employeeGateway.findAll();
    }

}
