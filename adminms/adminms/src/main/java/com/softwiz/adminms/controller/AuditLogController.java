package com.softwiz.adminms.controller;

import com.softwiz.adminms.entity.AuditLog;
import com.softwiz.adminms.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/audit-logs")

public class AuditLogController {
    @Autowired
    private AuditLogService auditLogService;
    @GetMapping("/{adminId}")
    /*public List<AuditLog> getAllLogsByAdmin(@PathVariable Long adminId) {
        return auditLogService.getAllLogsByAdmin(adminId);
    }*/

    public ResponseEntity<?> getAllLogsByAdmin(@PathVariable Long adminId) {
        try {
            List<AuditLog> logs = auditLogService.getAllLogsByAdmin(adminId);
            return ResponseEntity.ok(logs);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

