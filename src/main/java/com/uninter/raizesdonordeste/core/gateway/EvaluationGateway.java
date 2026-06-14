package com.uninter.raizesdonordeste.core.gateway;

import com.uninter.raizesdonordeste.core.domain.employee.EvaluationDomain;

import java.util.List;

public interface EvaluationGateway {

    EvaluationDomain save(EvaluationDomain evaluation);

    List<EvaluationDomain> findByEmployeeId(Long employeeId);

}
