package com.uninter.raizesdonordeste.core.usecase.promotion;

import com.uninter.raizesdonordeste.core.domain.audit.AuditAction;
import com.uninter.raizesdonordeste.core.domain.audit.Auditable;
import com.uninter.raizesdonordeste.core.domain.promotion.PromotionDomain;
import com.uninter.raizesdonordeste.core.gateway.PromotionGateway;
import com.uninter.raizesdonordeste.core.input.promotion.UpdatePromotionInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatePromotionUseCase {

    private final FindPromotionByIdUseCase findPromotionByIdUseCase;
    private final PromotionGateway promotionGateway;

    @Auditable(action = AuditAction.UPDATE)
    public PromotionDomain execute(final UpdatePromotionInput input) {
        var existing = findPromotionByIdUseCase.execute(input.id());
        return promotionGateway.save(
            existing.update(input.title(), input.startDate(), input.endDate(), input.reward()));
    }

}
