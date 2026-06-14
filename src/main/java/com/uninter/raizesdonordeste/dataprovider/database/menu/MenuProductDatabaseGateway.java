package com.uninter.raizesdonordeste.dataprovider.database.menu;

import com.uninter.raizesdonordeste.core.gateway.MenuProductGateway;
import com.uninter.raizesdonordeste.dataprovider.database.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MenuProductDatabaseGateway implements MenuProductGateway {

    private final MenuProductRepository menuProductRepository;
    private final MenuRepository menuRepository;
    private final ProductRepository productRepository;

    @Override
    public boolean existsById(final Long menuId, final Long productId) {
        return menuProductRepository.existsById(new MenuProductEntityId(menuId, productId));
    }

    @Override
    public void save(final Long menuId, final Long productId) {
        var menuEntity = menuRepository.findById(menuId).orElse(null);
        var productEntity = productRepository.findById(productId).orElse(null);
        var id = new MenuProductEntityId(menuId, productId);
        var menuProduct = MenuProductEntity.builder()
            .id(id)
            .menu(menuEntity)
            .product(productEntity)
            .createdAt(LocalDateTime.now())
            .build();
        menuProductRepository.save(menuProduct);
    }

    @Override
    public void deleteById(final Long menuId, final Long productId) {
        menuProductRepository.deleteById(new MenuProductEntityId(menuId, productId));
    }

}
