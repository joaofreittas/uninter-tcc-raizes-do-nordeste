package com.uninter.raizesdonordeste.core.usecase.unit;

import com.uninter.raizesdonordeste.core.domain.audit.AuditAction;
import com.uninter.raizesdonordeste.core.domain.audit.Auditable;
import com.uninter.raizesdonordeste.core.domain.unit.UnitDomain;
import com.uninter.raizesdonordeste.core.gateway.UnitGateway;
import com.uninter.raizesdonordeste.core.input.unit.UpdateUnitInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUnitUseCase {

    private final FindUnitByIdUseCase findUnitByIdUseCase;
    private final UnitGateway unitGateway;

    @Auditable(action = AuditAction.UPDATE)
    public UnitDomain execute(final UpdateUnitInput input) {
        var existing = findUnitByIdUseCase.execute(input.id());
        return unitGateway.save(existing.update(input.name(), input.address()));
    }

}
