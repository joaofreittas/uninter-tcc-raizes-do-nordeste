package com.uninter.raizesdonordeste.core.gateway;

import com.uninter.raizesdonordeste.core.domain.promotion.PromotionDomain;

import java.util.List;
import java.util.Optional;

public interface PromotionGateway {

    PromotionDomain save(PromotionDomain promotion);

    Optional<PromotionDomain> findById(Long id);

    List<PromotionDomain> findAll();

    void delete(PromotionDomain promotion);

}
