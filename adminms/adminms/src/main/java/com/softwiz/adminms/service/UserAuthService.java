package com.softwiz.adminms.service;

import com.softwiz.adminms.dto.UserLoginRequest;
import com.softwiz.adminms.entity.User;
import com.softwiz.adminms.repository.AdminUserRepository;
import com.softwiz.adminms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuditLogService auditLogService;
    @Autowired
    AdminUserRepository adminUserRepository;
    public Boolean authenticate(UserLoginRequest userLoginRequest) {
        String username = userLoginRequest.getUsername();
        String providedPassword = userLoginRequest.getPassword();
        User user = userRepository.findByUsername(username);
        if (user != null) {
            String storedPassword = user.getPassword();
            boolean isAuthenticated = isPasswordCorrect(providedPassword, storedPassword);
            if (isAuthenticated) {
                auditLogService.addLog(user.getCreatedByAdmin().getAdminId(), "User Login", "User", user.getUserId());
            }
            return isPasswordCorrect(providedPassword, storedPassword);
        }
        return false;
    }
    private boolean isPasswordCorrect(String providedPassword, String storedPassword) {
        return providedPassword.equals(storedPassword);
    }
}
