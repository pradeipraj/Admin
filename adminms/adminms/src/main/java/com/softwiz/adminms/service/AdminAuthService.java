package com.softwiz.adminms.service;

import com.softwiz.adminms.dto.AdminLoginRequest;
import com.softwiz.adminms.dto.UserDTO;
import com.softwiz.adminms.entity.AdminUser;
import com.softwiz.adminms.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminAuthService {
    @Autowired
    AdminUserRepository adminUserRepository;
    @Autowired
    private AuditLogService auditLogService;

    public Boolean authenticate(AdminLoginRequest adminLoginRequest) {
        String email = adminLoginRequest.getEmail();
        String providedPassword = adminLoginRequest.getPassword();
        AdminUser adminUser = adminUserRepository.findByEmail(email);
        if (adminUser != null) {
            String storedPassword = adminUser.getPassword();
            boolean isAuthenticated = isPasswordCorrect(providedPassword, storedPassword);
            if (isAuthenticated) {
                auditLogService.addLog(adminUser.getAdminId(), "Admin Login", "Admin User", adminUser.getAdminId());
            }
            return isAuthenticated;
        }
        return false;
    }
    private boolean isPasswordCorrect(String providedPassword, String storedPassword) {
        return providedPassword.equals(storedPassword);
    }
}

