package com.uninter.raizesdonordeste.core.usecase.menu;

import com.uninter.raizesdonordeste.core.domain.audit.AuditAction;
import com.uninter.raizesdonordeste.core.domain.audit.Auditable;
import com.uninter.raizesdonordeste.core.domain.menu.MenuDomain;
import com.uninter.raizesdonordeste.core.exception.DomainException;
import com.uninter.raizesdonordeste.core.gateway.MenuGateway;
import com.uninter.raizesdonordeste.core.gateway.UnitGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateMenuUseCase {

    private final MenuGateway menuGateway;
    private final UnitGateway unitGateway;

    @Auditable(action = AuditAction.CREATE)
    public MenuDomain execute(final Long unitId) {
        unitGateway.findById(unitId)
            .orElseThrow(() -> new DomainException("Unit not found: " + unitId));

        if (!menuGateway.findByUnitId(unitId).isEmpty()) {
            throw new DomainException("Unit " + unitId + " already has a menu");
        }

        return menuGateway.save(MenuDomain.create(unitId));
    }

}
