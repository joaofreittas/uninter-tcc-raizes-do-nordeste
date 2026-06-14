package com.uninter.raizesdonordeste.dataprovider.database.promotion;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromotionRepository extends JpaRepository<PromotionEntity, Long> {

    List<PromotionEntity> findByUnitId(Long unitId);

}
