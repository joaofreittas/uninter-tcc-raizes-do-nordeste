package com.uninter.raizesdonordeste.dataprovider.database.customer;

import com.uninter.raizesdonordeste.core.domain.customer.CustomerDomain;
import com.uninter.raizesdonordeste.dataprovider.database.promotion.CustomerPromotionEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String document;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String phone;

    @Column
    private LocalDate birthDate;

    @Column(nullable = false)
    private Boolean lgpdAccepted;

    @Column(nullable = false)
    private Boolean marketingAccepted;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<CustomerPromotionEntity> promotions;

    public CustomerDomain toDomain() {
        return CustomerDomain.builder()
            .id(id)
            .name(name)
            .document(document)
            .email(email)
            .phone(phone)
            .birthDate(birthDate)
            .lgpdAccepted(lgpdAccepted)
            .marketingAccepted(marketingAccepted)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .promotions(promotions == null ? List.of() : promotions.stream().map(CustomerPromotionEntity::toDomain).toList())
            .build();
    }

    public static CustomerEntity from(final CustomerDomain domain) {
        return CustomerEntity.builder()
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
