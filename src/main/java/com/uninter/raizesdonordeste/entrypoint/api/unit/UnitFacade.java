package com.uninter.raizesdonordeste.entrypoint.api.unit;

import com.uninter.raizesdonordeste.core.domain.unit.UnitDomain;
import com.uninter.raizesdonordeste.core.input.unit.CreateUnitInput;
import com.uninter.raizesdonordeste.core.input.unit.UpdateUnitInput;
import com.uninter.raizesdonordeste.core.usecase.unit.CreateUnitUseCase;
import com.uninter.raizesdonordeste.core.usecase.unit.DeactivateUnitUseCase;
import com.uninter.raizesdonordeste.core.usecase.unit.DeleteUnitUseCase;
import com.uninter.raizesdonordeste.core.usecase.unit.FindAllUnitsUseCase;
import com.uninter.raizesdonordeste.core.usecase.unit.FindUnitByIdUseCase;
import com.uninter.raizesdonordeste.core.usecase.unit.UpdateUnitUseCase;
import com.uninter.raizesdonordeste.entrypoint.api.unit.dto.CreateUnitRequest;
import com.uninter.raizesdonordeste.entrypoint.api.unit.dto.UnitResponse;
import com.uninter.raizesdonordeste.entrypoint.api.unit.dto.UpdateUnitRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UnitFacade {

    private final CreateUnitUseCase createUnitUseCase;
    private final FindAllUnitsUseCase findAllUnitsUseCase;
    private final FindUnitByIdUseCase findUnitByIdUseCase;
    private final UpdateUnitUseCase updateUnitUseCase;
    private final DeactivateUnitUseCase deactivateUnitUseCase;
    private final DeleteUnitUseCase deleteUnitUseCase;

    public UnitResponse create(final CreateUnitRequest request) {
        UnitDomain domain = createUnitUseCase.execute(
            new CreateUnitInput(request.name(), request.address()));
        return UnitResponse.fromDomain(domain);
    }

    public List<UnitResponse> findAll() {
        return findAllUnitsUseCase.execute().stream().map(UnitResponse::fromDomain).toList();
    }

    public UnitResponse findById(final Long id) {
        return UnitResponse.fromDomain(findUnitByIdUseCase.execute(id));
    }

    public UnitResponse update(final Long id, final UpdateUnitRequest request) {
        return UnitResponse.fromDomain(updateUnitUseCase.execute(
            new UpdateUnitInput(id, request.name(), request.address())));
    }

    public UnitResponse deactivate(final Long id) {
        return UnitResponse.fromDomain(deactivateUnitUseCase.execute(id));
    }

    public void delete(final Long id) {
        deleteUnitUseCase.execute(id);
    }

}
