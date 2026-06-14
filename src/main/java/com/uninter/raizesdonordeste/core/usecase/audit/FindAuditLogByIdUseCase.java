package com.uninter.raizesdonordeste.core.usecase.audit;

import com.uninter.raizesdonordeste.core.domain.audit.AuditLogDomain;
import com.uninter.raizesdonordeste.core.exception.ResourceNotFoundException;
import com.uninter.raizesdonordeste.core.gateway.AuditLogGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindAuditLogByIdUseCase {

    private final AuditLogGateway auditLogGateway;

    public AuditLogDomain execute(final Long id) {
        return auditLogGateway.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("AuditLog not found: " + id));
    }

}
