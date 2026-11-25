package com.examly.springapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.AuditLog;
import com.examly.springapp.repository.AuditLogRepository;
import com.examly.springapp.service.AuditLogService;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public List<AuditLog> getAuditLogs(String lockerNumber) {
        if (lockerNumber == null || lockerNumber.isBlank()) {
            return auditLogRepository.findAllByOrderByIdAsc();
        }
        return auditLogRepository.findByLockerNumberOrderByIdAsc(lockerNumber);
    }
}
