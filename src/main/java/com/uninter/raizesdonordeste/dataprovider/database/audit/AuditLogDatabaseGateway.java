package com.uninter.raizesdonordeste.dataprovider.database.audit;

import com.uninter.raizesdonordeste.core.domain.audit.AuditLogDomain;
import com.uninter.raizesdonordeste.core.gateway.AuditLogGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditLogDatabaseGateway implements AuditLogGateway {

    private final AuditLogRepository repository;

    @Override
    public AuditLogDomain save(final AuditLogDomain log) {
        return repository.save(AuditLogEntity.from(log)).toDomain();
    }

    @Override
    public Optional<AuditLogDomain> findById(final Long id) {
        return repository.findById(id).map(AuditLogEntity::toDomain);
    }

    @Override
    public List<AuditLogDomain> findAll() {
        return repository.findAll().stream().map(AuditLogEntity::toDomain).toList();
    }

}
