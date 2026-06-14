package com.uninter.raizesdonordeste.core.domain.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDomain {

    private Long id;
    private String name;
    private String document;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private Boolean lgpdAccepted;
    private Boolean marketingAccepted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CustomerDomain create(final String name, final String document, final String email,
                                        final String phone, final LocalDate birthDate,
                                        final Boolean lgpdAccepted, final Boolean marketingAccepted) {
        var now = LocalDateTime.now();
        return CustomerDomain.builder()
            .name(name)
            .document(document)
            .email(email)
            .phone(phone)
            .birthDate(birthDate)
            .lgpdAccepted(lgpdAccepted)
            .marketingAccepted(marketingAccepted != null ? marketingAccepted : Boolean.FALSE)
            .createdAt(now)
            .build();
    }

    public CustomerDomain update(final String name, final String phone,
                                 final LocalDate birthDate, final Boolean marketingAccepted) {
        return CustomerDomain.builder()
            .id(this.id)
            .name(name)
            .document(this.document)
            .email(this.email)
            .phone(phone)
            .birthDate(birthDate)
            .lgpdAccepted(this.lgpdAccepted)
            .marketingAccepted(marketingAccepted)
            .createdAt(this.createdAt)
            .updatedAt(LocalDateTime.now())
            .build();
    }

}
