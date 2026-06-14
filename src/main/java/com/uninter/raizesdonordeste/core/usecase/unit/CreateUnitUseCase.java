package com.uninter.raizesdonordeste.core.usecase.unit;

import com.uninter.raizesdonordeste.core.domain.audit.AuditAction;
import com.uninter.raizesdonordeste.core.domain.audit.Auditable;
import com.uninter.raizesdonordeste.core.domain.unit.UnitDomain;
import com.uninter.raizesdonordeste.core.gateway.UnitGateway;
import com.uninter.raizesdonordeste.core.input.unit.CreateUnitInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUnitUseCase {

    private final UnitGateway unitGateway;

    @Auditable(action = AuditAction.CREATE)
    public UnitDomain execute(final CreateUnitInput input) {
        return unitGateway.save(UnitDomain.create(input.name(), input.address()));
    }

}
