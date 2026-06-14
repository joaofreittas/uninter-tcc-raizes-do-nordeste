package com.uninter.raizesdonordeste.core.usecase.promotion;

import com.uninter.raizesdonordeste.core.domain.promotion.PromotionDomain;
import com.uninter.raizesdonordeste.core.gateway.PromotionGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllPromotionsUseCase {

    private final PromotionGateway promotionGateway;

    public List<PromotionDomain> execute() {
        return promotionGateway.findAll();
    }

}
