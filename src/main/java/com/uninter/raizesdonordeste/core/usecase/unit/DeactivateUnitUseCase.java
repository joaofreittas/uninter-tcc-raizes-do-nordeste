package com.uninter.raizesdonordeste.core.usecase.unit;

import com.uninter.raizesdonordeste.core.domain.audit.AuditAction;
import com.uninter.raizesdonordeste.core.domain.audit.Auditable;
import com.uninter.raizesdonordeste.core.domain.unit.UnitDomain;
import com.uninter.raizesdonordeste.core.gateway.UnitGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeactivateUnitUseCase {

    private final FindUnitByIdUseCase findUnitByIdUseCase;
    private final UnitGateway unitGateway;

    @Auditable(action = AuditAction.DEACTIVATE)
    public UnitDomain execute(final Long id) {
        var existing = findUnitByIdUseCase.execute(id);
        var deactivated = existing.deactivate();
        return unitGateway.save(deactivated);
    }

}
