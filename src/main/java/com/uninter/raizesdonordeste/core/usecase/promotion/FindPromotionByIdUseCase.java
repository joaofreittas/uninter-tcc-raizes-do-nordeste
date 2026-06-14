package com.uninter.raizesdonordeste.core.usecase.promotion;

import com.uninter.raizesdonordeste.core.domain.promotion.PromotionDomain;
import com.uninter.raizesdonordeste.core.exception.ResourceNotFoundException;
import com.uninter.raizesdonordeste.core.gateway.PromotionGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindPromotionByIdUseCase {

    private final PromotionGateway promotionGateway;

    public PromotionDomain execute(final Long id) {
        return promotionGateway.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Promotion not found: " + id));
    }

}
