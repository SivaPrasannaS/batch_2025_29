package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.model.AuditLog;

public interface AuditLogService {

    List<AuditLog> getAuditLogs(String lockerNumber);
}
