package com.uninter.raizesdonordeste.core.usecase.menu;

import com.uninter.raizesdonordeste.core.domain.audit.AuditAction;
import com.uninter.raizesdonordeste.core.domain.audit.Auditable;
import com.uninter.raizesdonordeste.core.gateway.MenuGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteMenuUseCase {

    private final FindMenuByIdUseCase findMenuByIdUseCase;
    private final MenuGateway menuGateway;

    @Auditable(action = AuditAction.DELETE)
    public void execute(final Long id) {
        var menu = findMenuByIdUseCase.execute(id);
        menuGateway.delete(menu);
    }

}
