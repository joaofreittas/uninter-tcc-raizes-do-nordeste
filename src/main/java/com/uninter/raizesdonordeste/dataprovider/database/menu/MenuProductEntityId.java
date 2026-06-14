package com.uninter.raizesdonordeste.dataprovider.database.menu;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class MenuProductEntityId implements Serializable {

    @Column(name = "menu_id", nullable = false)
    private Long menuId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

}
