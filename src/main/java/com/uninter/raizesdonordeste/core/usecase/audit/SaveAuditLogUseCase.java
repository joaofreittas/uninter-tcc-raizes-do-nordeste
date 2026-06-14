package com.uninter.raizesdonordeste.core.usecase.audit;

import com.uninter.raizesdonordeste.core.domain.audit.AuditLogDomain;
import com.uninter.raizesdonordeste.core.gateway.AuditLogGateway;
import com.uninter.raizesdonordeste.core.input.audit.SaveAuditLogInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveAuditLogUseCase {

    private final AuditLogGateway auditLogGateway;

    public AuditLogDomain execute(final SaveAuditLogInput input) {
        return auditLogGateway.save(
            AuditLogDomain.create(input.userEmail(), input.action(),
                input.entity(), input.entityId(), input.occurredAt()));
    }

}
