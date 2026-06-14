package com.uninter.raizesdonordeste.dataprovider.database.menu;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuProductRepository extends JpaRepository<MenuProductEntity, MenuProductEntityId> {

    List<MenuProductEntity> findByIdMenuId(Long menuId);

}
