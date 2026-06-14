package com.uninter.raizesdonordeste.core.gateway;

public interface MenuProductGateway {

    boolean existsById(Long menuId, Long productId);

    void save(Long menuId, Long productId);

    void deleteById(Long menuId, Long productId);

}
