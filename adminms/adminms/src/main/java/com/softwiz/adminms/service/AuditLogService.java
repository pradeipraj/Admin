package com.softwiz.adminms.service;

import com.softwiz.adminms.entity.AdminUser;
import com.softwiz.adminms.entity.AuditLog;
import com.softwiz.adminms.repository.AdminUserRepository;
import com.softwiz.adminms.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuditLogService {
    @Autowired
    private AuditLogRepository auditLogRepository;
    @Autowired
    private AdminUserRepository adminUserRepository;
    public void addLog(Long adminId, String action, String entityType, Long entityId) {
        AuditLog log = new AuditLog();
        log.setAdminId(adminId);
        log.setAction(action);
        log.setEntityType(entityType);
        log.setEntityId(entityId);
        log.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(log);
    }

    public List<AuditLog> getAllLogsByAdmin(Long adminId) {
        Optional<AdminUser> adminUserOptional = adminUserRepository.findById(adminId);
        if (adminUserOptional.isEmpty()) {
            throw new IllegalArgumentException("Admin user not found");
        }
        return auditLogRepository.findAllByAdminId(adminId);
    }
}
