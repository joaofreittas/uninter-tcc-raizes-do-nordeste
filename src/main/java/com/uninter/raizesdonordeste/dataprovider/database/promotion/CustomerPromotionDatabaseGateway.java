package com.uninter.raizesdonordeste.dataprovider.database.promotion;

import com.uninter.raizesdonordeste.core.domain.promotion.CustomerPromotionDomain;
import com.uninter.raizesdonordeste.core.gateway.CustomerPromotionGateway;
import com.uninter.raizesdonordeste.dataprovider.database.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerPromotionDatabaseGateway implements CustomerPromotionGateway {

    private final CustomerPromotionRepository repository;
    private final CustomerRepository customerRepository;
    private final PromotionRepository promotionRepository;

    @Override
    public CustomerPromotionDomain save(final CustomerPromotionDomain customerPromotion) {
        var customerEntity = customerRepository.findById(customerPromotion.getCustomerId()).orElse(null);
        var promotionEntity = promotionRepository.findById(customerPromotion.getPromotionId()).orElse(null);
        return repository.save(CustomerPromotionEntity.from(customerPromotion, customerEntity, promotionEntity)).toDomain();
    }

    @Override
    public Optional<CustomerPromotionDomain> findById(final Long id) {
        return repository.findById(id).map(CustomerPromotionEntity::toDomain);
    }

    @Override
    public List<CustomerPromotionDomain> findByCustomerId(final Long customerId) {
        return repository.findByCustomerId(customerId).stream().map(CustomerPromotionEntity::toDomain).toList();
    }

    @Override
    public List<CustomerPromotionDomain> findByPromotionId(final Long promotionId) {
        return repository.findByPromotionId(promotionId).stream().map(CustomerPromotionEntity::toDomain).toList();
    }

    @Override
    public void delete(final CustomerPromotionDomain customerPromotion) {
        repository.deleteById(customerPromotion.getId());
    }

}
