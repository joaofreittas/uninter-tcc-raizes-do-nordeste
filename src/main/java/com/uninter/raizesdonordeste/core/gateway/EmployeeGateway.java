package com.uninter.raizesdonordeste.core.gateway;

import com.uninter.raizesdonordeste.core.domain.employee.EmployeeDomain;

import java.util.List;
import java.util.Optional;

public interface EmployeeGateway {

    EmployeeDomain save(EmployeeDomain employee);

    Optional<EmployeeDomain> findById(Long id);

    Optional<EmployeeDomain> findByDocument(String document);

    List<EmployeeDomain> findAll();

    void delete(EmployeeDomain employee);

}
