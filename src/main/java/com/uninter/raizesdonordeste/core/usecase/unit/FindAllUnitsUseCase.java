package com.uninter.raizesdonordeste.core.usecase.unit;

import com.uninter.raizesdonordeste.core.domain.unit.UnitDomain;
import com.uninter.raizesdonordeste.core.gateway.UnitGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllUnitsUseCase {

    private final UnitGateway unitGateway;

    public List<UnitDomain> execute() {
        return unitGateway.findAll();
    }

}
