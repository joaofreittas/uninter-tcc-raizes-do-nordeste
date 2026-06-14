package com.uninter.raizesdonordeste.dataprovider.database.audit;

import com.uninter.raizesdonordeste.core.domain.audit.AuditAction;
import com.uninter.raizesdonordeste.core.domain.audit.AuditLogDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "audit_logs")
public class AuditLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditAction action;

    @Column(nullable = false)
    private String entity;

    @Column
    private Long entityId;

    @Column(nullable = false)
    private LocalDateTime occurredAt;

    public AuditLogDomain toDomain() {
        return AuditLogDomain.builder()
            .id(id)
            .userEmail(userEmail)
            .action(action)
            .entity(entity)
            .entityId(entityId)
            .occurredAt(occurredAt)
            .build();
    }

    public static AuditLogEntity from(final AuditLogDomain domain) {
        return AuditLogEntity.builder()
            .id(domain.getId())
            .userEmail(domain.getUserEmail())
            .action(domain.getAction())
            .entity(domain.getEntity())
            .entityId(domain.getEntityId())
            .occurredAt(domain.getOccurredAt())
            .build();
    }

}
