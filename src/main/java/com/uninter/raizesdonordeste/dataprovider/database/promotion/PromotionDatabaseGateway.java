package com.uninter.raizesdonordeste.dataprovider.database.promotion;

import com.uninter.raizesdonordeste.core.domain.promotion.PromotionDomain;
import com.uninter.raizesdonordeste.core.gateway.PromotionGateway;
import com.uninter.raizesdonordeste.dataprovider.database.unit.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PromotionDatabaseGateway implements PromotionGateway {

    private final PromotionRepository repository;
    private final UnitRepository unitRepository;

    @Override
    public PromotionDomain save(final PromotionDomain promotion) {
        var unitEntity = unitRepository.findById(promotion.getUnitId()).orElse(null);
        return repository.save(PromotionEntity.from(promotion, unitEntity)).toDomain();
    }

    @Override
    public Optional<PromotionDomain> findById(final Long id) {
        return repository.findById(id).map(PromotionEntity::toDomain);
    }

    @Override
    public List<PromotionDomain> findAll() {
        return repository.findAll().stream().map(PromotionEntity::toDomain).toList();
    }

    @Override
    public void delete(final PromotionDomain promotion) {
        repository.deleteById(promotion.getId());
    }

}
