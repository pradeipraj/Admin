package com.softwiz.adminms.service;

import com.softwiz.adminms.dto.AdminLoginRequest;
import com.softwiz.adminms.entity.AdminUser;
import com.softwiz.adminms.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminAuthService {
    @Autowired
    AdminUserRepository adminUserRepository;
    public String authenticate(AdminLoginRequest adminLoginRequest) {

        AdminUser adminUser = adminUserRepository.findByEmail(adminLoginRequest.getEmail());
        return adminUser.getUsername();

    }

}
