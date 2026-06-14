package com.uninter.raizesdonordeste.core.usecase.unit;

import com.uninter.raizesdonordeste.core.domain.unit.UnitDomain;
import com.uninter.raizesdonordeste.core.exception.ResourceNotFoundException;
import com.uninter.raizesdonordeste.core.gateway.UnitGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindUnitByIdUseCase {

    private final UnitGateway unitGateway;

    public UnitDomain execute(final Long id) {
        return unitGateway.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Unit not found: " + id));
    }

}
