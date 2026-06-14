package com.uninter.raizesdonordeste.core.gateway;

import com.uninter.raizesdonordeste.core.domain.unit.UnitDomain;

import java.util.List;
import java.util.Optional;

public interface UnitGateway {

    UnitDomain save(UnitDomain unit);

    Optional<UnitDomain> findById(Long id);

    List<UnitDomain> findAll();

    void delete(UnitDomain unit);

}
