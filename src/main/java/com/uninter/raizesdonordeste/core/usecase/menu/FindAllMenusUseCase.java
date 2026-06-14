package com.uninter.raizesdonordeste.core.usecase.menu;

import com.uninter.raizesdonordeste.core.domain.menu.MenuDomain;
import com.uninter.raizesdonordeste.core.gateway.MenuGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindAllMenusUseCase {

    private final MenuGateway menuGateway;

    public List<MenuDomain> execute(final Optional<Long> unitId) {
        return unitId.map(menuGateway::findByUnitId)
            .orElseGet(menuGateway::findAll);
    }

}
