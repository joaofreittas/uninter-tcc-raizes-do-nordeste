package com.uninter.raizesdonordeste.core.input.audit;

import com.uninter.raizesdonordeste.core.domain.audit.AuditAction;

import java.time.LocalDateTime;

public record SaveAuditLogInput(
    String userEmail,
    AuditAction action,
    String entity,
    Long entityId,
    LocalDateTime occurredAt
) {

}
