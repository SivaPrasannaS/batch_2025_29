package com.examly.springapp.aspect;

import java.time.LocalDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.examly.springapp.model.AuditLog;
import com.examly.springapp.model.Locker;
import com.examly.springapp.repository.AuditLogRepository;

@Aspect
@Component
public class AuditAspect {

    private static final Logger logger = LoggerFactory.getLogger(AuditAspect.class);

    private final AuditLogRepository auditLogRepository;

    public AuditAspect(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @AfterReturning(pointcut = "execution(* com.examly.springapp.service.impl.LockerServiceImpl.*Locker(..))", returning = "result")
    public void recordAudit(JoinPoint joinPoint, Object result) {
        if (result instanceof Locker locker) {
            String action = joinPoint.getSignature().getName();
            AuditLog entry = new AuditLog();
            entry.setAction(action);
            entry.setLockerNumber(locker.getLockerNumber());
            entry.setPerformedAt(LocalDateTime.now());
            auditLogRepository.save(entry);
            logger.info("Audit: {} on locker {}", action, locker.getLockerNumber());
        }
    }
}
