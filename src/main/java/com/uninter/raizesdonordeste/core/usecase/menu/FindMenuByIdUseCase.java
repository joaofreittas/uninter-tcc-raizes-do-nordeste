package com.uninter.raizesdonordeste.core.usecase.menu;

import com.uninter.raizesdonordeste.core.domain.menu.MenuDomain;
import com.uninter.raizesdonordeste.core.exception.ResourceNotFoundException;
import com.uninter.raizesdonordeste.core.gateway.MenuGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindMenuByIdUseCase {

    private final MenuGateway menuGateway;

    public MenuDomain execute(final Long id) {
        return menuGateway.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Menu not found: " + id));
    }

}
