package com.uninter.raizesdonordeste.entrypoint.api.customer.dto;

import com.uninter.raizesdonordeste.core.domain.customer.CustomerDomain;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    LocalDateTime updatedAt
) {

    public static CustomerResponse fromDomain(final CustomerDomain domain) {
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
            .build();
    }

}
