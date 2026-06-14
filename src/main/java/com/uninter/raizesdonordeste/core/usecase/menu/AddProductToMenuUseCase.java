package com.uninter.raizesdonordeste.core.usecase.menu;

import com.uninter.raizesdonordeste.core.domain.audit.AuditAction;
import com.uninter.raizesdonordeste.core.domain.audit.Auditable;
import com.uninter.raizesdonordeste.core.domain.menu.MenuDomain;
import com.uninter.raizesdonordeste.core.exception.DomainException;
import com.uninter.raizesdonordeste.core.gateway.MenuGateway;
import com.uninter.raizesdonordeste.core.gateway.MenuProductGateway;
import com.uninter.raizesdonordeste.core.gateway.ProductGateway;
import com.uninter.raizesdonordeste.core.input.menu.AddProductToMenuInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddProductToMenuUseCase {

    private final FindMenuByIdUseCase findMenuByIdUseCase;
    private final ProductGateway productGateway;
    private final MenuProductGateway menuProductGateway;
    private final MenuGateway menuGateway;

    @Auditable(action = AuditAction.UPDATE, entity = "Menu.addProduct")
    public MenuDomain execute(final AddProductToMenuInput input) {
        var menu = findMenuByIdUseCase.execute(input.menuId());
        productGateway.findById(input.productId())
            .orElseThrow(() -> new DomainException("Product not found: " + input.productId()));

        if (menuProductGateway.existsById(input.menuId(), input.productId())) {
            throw new DomainException("Product " + input.productId() + " is already in menu " + input.menuId());
        }

        menuProductGateway.save(input.menuId(), input.productId());
        menuGateway.save(menu.touch());

        return findMenuByIdUseCase.execute(input.menuId());
    }

}
