package com.uninter.raizesdonordeste.core.usecase.menu;

import com.uninter.raizesdonordeste.core.domain.audit.AuditAction;
import com.uninter.raizesdonordeste.core.domain.audit.Auditable;
import com.uninter.raizesdonordeste.core.domain.menu.MenuDomain;
import com.uninter.raizesdonordeste.core.exception.DomainException;
import com.uninter.raizesdonordeste.core.gateway.MenuProductGateway;
import com.uninter.raizesdonordeste.core.input.menu.RemoveProductFromMenuInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveProductFromMenuUseCase {

    private final FindMenuByIdUseCase findMenuByIdUseCase;
    private final MenuProductGateway menuProductGateway;

    @Auditable(action = AuditAction.UPDATE, entity = "Menu.removeProduct")
    public MenuDomain execute(final RemoveProductFromMenuInput input) {
        findMenuByIdUseCase.execute(input.menuId());

        if (!menuProductGateway.existsById(input.menuId(), input.productId())) {
            throw new DomainException(
                "Product " + input.productId() + " not found in menu " + input.menuId());
        }

        menuProductGateway.deleteById(input.menuId(), input.productId());
        return findMenuByIdUseCase.execute(input.menuId());
    }

}
