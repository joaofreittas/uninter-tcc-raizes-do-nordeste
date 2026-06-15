package com.uninter.raizesdonordeste.entrypoint.api.employee;

import com.uninter.raizesdonordeste.core.input.employee.AddEvaluationInput;
import com.uninter.raizesdonordeste.core.input.employee.CreateEmployeeInput;
import com.uninter.raizesdonordeste.core.input.employee.UpdateEmployeeInput;
import com.uninter.raizesdonordeste.core.usecase.employee.AddEvaluationUseCase;
import com.uninter.raizesdonordeste.core.usecase.employee.CreateEmployeeUseCase;
import com.uninter.raizesdonordeste.core.usecase.employee.DeleteEmployeeUseCase;
import com.uninter.raizesdonordeste.core.usecase.employee.FindAllEmployeesUseCase;
import com.uninter.raizesdonordeste.core.usecase.employee.FindEmployeeByIdUseCase;
import com.uninter.raizesdonordeste.core.usecase.employee.UpdateEmployeeUseCase;
import com.uninter.raizesdonordeste.entrypoint.api.employee.dto.AddEvaluationRequest;
import com.uninter.raizesdonordeste.entrypoint.api.employee.dto.CreateEmployeeRequest;
import com.uninter.raizesdonordeste.entrypoint.api.employee.dto.EmployeeResponse;
import com.uninter.raizesdonordeste.entrypoint.api.employee.dto.EvaluationResponse;
import com.uninter.raizesdonordeste.entrypoint.api.employee.dto.UpdateEmployeeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmployeeFacade {

    private final CreateEmployeeUseCase createEmployeeUseCase;
    private final FindAllEmployeesUseCase findAllEmployeesUseCase;
    private final FindEmployeeByIdUseCase findEmployeeByIdUseCase;
    private final UpdateEmployeeUseCase updateEmployeeUseCase;
    private final AddEvaluationUseCase addEvaluationUseCase;
    private final DeleteEmployeeUseCase deleteEmployeeUseCase;

    public EmployeeResponse create(final CreateEmployeeRequest request) {
        var domain = createEmployeeUseCase.execute(new CreateEmployeeInput(
            request.unitId(), request.name(), request.address(),
            request.document(), request.birthDate(), request.type()));
        return EmployeeResponse.fromDomain(domain);
    }

    public List<EmployeeResponse> findAll() {
        return findAllEmployeesUseCase.execute().stream().map(EmployeeResponse::fromDomain).toList();
    }

    public EmployeeResponse findById(final Long id) {
        return EmployeeResponse.fromDomain(findEmployeeByIdUseCase.execute(id));
    }

    public EmployeeResponse update(final Long id, final UpdateEmployeeRequest request) {
        var domain = updateEmployeeUseCase.execute(new UpdateEmployeeInput(
            id, request.name(), request.address(), request.birthDate(), request.type()));
        return EmployeeResponse.fromDomain(domain);
    }

    public EvaluationResponse addEvaluation(final Long employeeId, final AddEvaluationRequest request) {
        var domain = addEvaluationUseCase.execute(
            new AddEvaluationInput(employeeId, request.rating(), request.comment()));
        return EvaluationResponse.fromDomain(domain);
    }

    public void delete(final Long id) {
        deleteEmployeeUseCase.execute(id);
    }

}
