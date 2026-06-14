package com.uninter.raizesdonordeste.core.domain.audit;

import java.time.LocalDateTime;

public record AuditEvent(
    String userEmail,
    AuditAction action,
    String entity,
    Long entityId,
    LocalDateTime occurredAt
) {

}
