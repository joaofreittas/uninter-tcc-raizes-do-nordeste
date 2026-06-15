package com.uninter.raizesdonordeste.entrypoint.api.customer.dto;

import com.uninter.raizesdonordeste.core.domain.customer.CustomerDomain;
import com.uninter.raizesdonordeste.core.domain.promotion.CustomerPromotionDomain;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CustomerResponse(
    Long id,
    String name,
    String document,
    String email,
    String phone,
    LocalDate birthDate,
    Boolean lgpdAccepted,
    Boolean marketingAccepted,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    List<CustomerPromotionItem> promotions
) {

    @Builder
    public record CustomerPromotionItem(
        Long id,
        Long promotionId,
        String promotionTitle,
        Boolean active,
        LocalDateTime assignedAt
    ) {
        public static CustomerPromotionItem fromDomain(final CustomerPromotionDomain domain) {
            return CustomerPromotionItem.builder()
                .id(domain.getId())
                .promotionId(domain.getPromotionId())
                .promotionTitle(domain.getPromotionTitle())
                .active(domain.getActive())
                .assignedAt(domain.getAssignedAt())
                .build();
        }
    }

    public static CustomerResponse fromDomain(final CustomerDomain domain) {
        var promotions = domain.getPromotions() == null ? List.<CustomerPromotionItem>of()
            : domain.getPromotions().stream().map(CustomerPromotionItem::fromDomain).toList();
        return CustomerResponse.builder()
            .id(domain.getId())
            .name(domain.getName())
            .document(domain.getDocument())
            .email(domain.getEmail())
            .phone(domain.getPhone())
            .birthDate(domain.getBirthDate())
            .lgpdAccepted(domain.getLgpdAccepted())
            .marketingAccepted(domain.getMarketingAccepted())
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .promotions(promotions)
            .build();
    }

}
