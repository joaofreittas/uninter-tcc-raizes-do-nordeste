package com.uninter.raizesdonordeste.entrypoint.api.audit;

import com.uninter.raizesdonordeste.core.usecase.audit.FindAllAuditLogsUseCase;
import com.uninter.raizesdonordeste.core.usecase.audit.FindAuditLogByIdUseCase;
import com.uninter.raizesdonordeste.entrypoint.api.audit.dto.AuditLogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuditLogFacade {

    private final FindAllAuditLogsUseCase findAllAuditLogsUseCase;
    private final FindAuditLogByIdUseCase findAuditLogByIdUseCase;

    public List<AuditLogResponse> findAll() {
        return findAllAuditLogsUseCase.execute().stream().map(AuditLogResponse::fromDomain).toList();
    }

    public AuditLogResponse findById(final Long id) {
        return AuditLogResponse.fromDomain(findAuditLogByIdUseCase.execute(id));
    }

}
