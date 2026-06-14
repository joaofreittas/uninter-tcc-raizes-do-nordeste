package com.uninter.raizesdonordeste.dataprovider.database.employee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<EvaluationEntity, Long> {

    List<EvaluationEntity> findByEmployeeId(Long employeeId);

}
