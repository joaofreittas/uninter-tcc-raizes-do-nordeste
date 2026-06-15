package com.uninter.raizesdonordeste.entrypoint.api.customer;

import com.uninter.raizesdonordeste.core.domain.user.Role;
import com.uninter.raizesdonordeste.core.input.auth.RegisterUserInput;
import com.uninter.raizesdonordeste.core.input.customer.UpdateCustomerInput;
import com.uninter.raizesdonordeste.core.usecase.auth.RegisterUserUseCase;
import com.uninter.raizesdonordeste.core.usecase.customer.CreateCustomerUseCase;
import com.uninter.raizesdonordeste.core.usecase.customer.DeleteCustomerUseCase;
import com.uninter.raizesdonordeste.core.usecase.customer.FindAllCustomersUseCase;
import com.uninter.raizesdonordeste.core.usecase.customer.FindCustomerByIdUseCase;
import com.uninter.raizesdonordeste.core.usecase.customer.UpdateCustomerUseCase;
import com.uninter.raizesdonordeste.entrypoint.api.customer.dto.CreateCustomerRequest;
import com.uninter.raizesdonordeste.entrypoint.api.customer.dto.CustomerResponse;
import com.uninter.raizesdonordeste.entrypoint.api.customer.dto.UpdateCustomerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomerFacade {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final FindAllCustomersUseCase findAllCustomersUseCase;
    private final FindCustomerByIdUseCase findCustomerByIdUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;
    private final RegisterUserUseCase registerUserUseCase;

    public CustomerResponse create(final CreateCustomerRequest request) {
        var domain = createCustomerUseCase.execute(request.toInput());

        registerUserUseCase.execute(new RegisterUserInput(request.name(), request.email(), request.password(), Role.USER));

        return CustomerResponse.fromDomain(domain);
    }

    public List<CustomerResponse> findAll() {
        return findAllCustomersUseCase.execute().stream().map(CustomerResponse::fromDomain).toList();
    }

    public CustomerResponse findById(final Long id) {
        return CustomerResponse.fromDomain(findCustomerByIdUseCase.execute(id));
    }

    public CustomerResponse update(final Long id, final UpdateCustomerRequest request) {
        var domain = updateCustomerUseCase.execute(new UpdateCustomerInput(
            id, request.name(), request.phone(), request.birthDate(), request.marketingAccepted()));
        return CustomerResponse.fromDomain(domain);
    }

    public void delete(final Long id) {
        deleteCustomerUseCase.execute(id);
    }

}
