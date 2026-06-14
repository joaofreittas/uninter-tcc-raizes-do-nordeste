package com.uninter.raizesdonordeste.dataprovider.database.employee;

import com.uninter.raizesdonordeste.core.domain.employee.EmployeeDomain;
import com.uninter.raizesdonordeste.core.gateway.EmployeeGateway;
import com.uninter.raizesdonordeste.dataprovider.database.unit.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmployeeDatabaseGateway implements EmployeeGateway {

    private final EmployeeRepository employeeRepository;
    private final UnitRepository unitRepository;

    @Override
    public EmployeeDomain save(final EmployeeDomain employee) {
        var unitEntity = unitRepository.findById(employee.getUnitId()).orElse(null);
        return employeeRepository.save(EmployeeEntity.from(employee, unitEntity)).toDomain();
    }

    @Override
    public Optional<EmployeeDomain> findById(final Long id) {
        return employeeRepository.findById(id).map(EmployeeEntity::toDomain);
    }

    @Override
    public Optional<EmployeeDomain> findByDocument(final String document) {
        return employeeRepository.findByDocument(document).map(EmployeeEntity::toDomain);
    }

    @Override
    public List<EmployeeDomain> findAll() {
        return employeeRepository.findAll().stream().map(EmployeeEntity::toDomain).toList();
    }

    @Override
    public void delete(final EmployeeDomain employee) {
        employeeRepository.deleteById(employee.getId());
    }

}
