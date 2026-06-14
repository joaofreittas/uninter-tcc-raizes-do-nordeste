package com.uninter.raizesdonordeste.core.usecase.unit;

import com.uninter.raizesdonordeste.core.domain.audit.AuditAction;
import com.uninter.raizesdonordeste.core.domain.audit.Auditable;
import com.uninter.raizesdonordeste.core.gateway.UnitGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUnitUseCase {

    private final FindUnitByIdUseCase findUnitByIdUseCase;
    private final UnitGateway unitGateway;

    @Auditable(action = AuditAction.DELETE)
    public void execute(final Long id) {
        var unit = findUnitByIdUseCase.execute(id);
        unitGateway.delete(unit);
    }

}
