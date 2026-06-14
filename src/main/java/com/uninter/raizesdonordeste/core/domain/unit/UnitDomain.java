package com.uninter.raizesdonordeste.core.domain.unit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnitDomain {

    private Long id;
    private String name;
    private String address;
    private UnitStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UnitDomain create(final String name, final String address) {
        return UnitDomain.builder()
            .name(name)
            .address(address)
            .status(UnitStatus.ACTIVATED)
            .createdAt(LocalDateTime.now())
            .build();
    }

    public UnitDomain update(final String name, final String address) {
        return UnitDomain.builder()
            .id(this.id)
            .name(name)
            .address(address)
            .status(this.status)
            .createdAt(this.createdAt)
            .updatedAt(LocalDateTime.now())
            .build();
    }

    public UnitDomain deactivate() {
        return UnitDomain.builder()
            .id(this.id)
            .name(this.name)
            .address(this.address)
            .status(UnitStatus.INACTIVE)
            .createdAt(this.createdAt)
            .updatedAt(LocalDateTime.now())
            .build();
    }

}
