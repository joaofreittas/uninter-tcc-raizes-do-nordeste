package com.uninter.raizesdonordeste.core.domain.audit;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private static final String ANONYMOUS = "anonymous";

    private final ApplicationEventPublisher eventPublisher;

    @Around("@annotation(auditable)")
    public Object intercept(ProceedingJoinPoint joinPoint, Auditable auditable) throws Throwable {
        var result = joinPoint.proceed();

        publishEvent(joinPoint, auditable);

        return result;
    }

    private void publishEvent(ProceedingJoinPoint joinPoint, Auditable auditable) {
        var userEmail = resolveUserEmail();
        var entity = resolveEntityName(joinPoint, auditable);
        var entityId = resolveEntityId(joinPoint, auditable);
        eventPublisher.publishEvent(new AuditEvent(userEmail, auditable.action(), entity, entityId, LocalDateTime.now()));
    }

    private String resolveUserEmail() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return ANONYMOUS;
    }

    private String resolveEntityName(ProceedingJoinPoint joinPoint, Auditable auditable) {
        if (!auditable.entity().isBlank()) {
            return auditable.entity();
        }
        return joinPoint.getTarget().getClass().getSimpleName();
    }

    private Long resolveEntityId(ProceedingJoinPoint joinPoint, Auditable auditable) {
        if (auditable.action() == AuditAction.CREATE) {
            return null;
        }
        var args = joinPoint.getArgs();
        var index = auditable.entityIdArgIndex();
        if (index < args.length && args[index] instanceof Long id) {
            return id;
        }
        return null;
    }

}
