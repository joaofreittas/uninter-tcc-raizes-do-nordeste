package com.uninter.raizesdonordeste.dataprovider.database.menu;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {

    List<MenuEntity> findByUnitId(Long unitId);

}
