package com.uninter.raizesdonordeste.core.domain.audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogDomain {

    private Long id;
    private String userEmail;
    private AuditAction action;
    private String entity;
    private Long entityId;
    private LocalDateTime occurredAt;

    public static AuditLogDomain create(final String userEmail, final AuditAction action,
                                        final String entity, final Long entityId,
                                        final LocalDateTime occurredAt) {
        return AuditLogDomain.builder()
            .userEmail(userEmail)
            .action(action)
            .entity(entity)
            .entityId(entityId)
            .occurredAt(occurredAt)
            .build();
    }

}
