package com.softwiz.adminms.service;

import com.softwiz.adminms.dto.AdminUserDTO;
import com.softwiz.adminms.dto.UserDTO;
import com.softwiz.adminms.entity.AdminUser;
import com.softwiz.adminms.entity.User;
import com.softwiz.adminms.repository.AdminUserRepository;
import com.softwiz.adminms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserService {
    @Autowired
    private AdminUserRepository adminUserRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuditLogService auditLogService;
    public AdminUser createAdminUser(AdminUserDTO adminUserDTO) {
        // Convert DTO to Entity
        AdminUser adminUser = new AdminUser();
        adminUser.setEmail(adminUserDTO.getEmail());
        adminUser.setUsername(adminUserDTO.getUsername());
        adminUser.setPassword(adminUserDTO.getPassword());
        adminUser.setAdminId(adminUserDTO.getAdminId());
        // Save the AdminUser entity
        adminUserRepository.save(adminUser);
        auditLogService.addLog(adminUser.getAdminId(), "Create Admin", "Admin User", adminUser.getAdminId());
        return adminUser;
    }

    public User createUserByAdmin(UserDTO userDTO, Long adminId) {
        AdminUser createdByAdmin = adminUserRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Admin user not found"));
        // Convert DTO to User entity
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setIsEnable(userDTO.getIsEnable());
        user.setCreatedByAdmin(createdByAdmin); // Set the admin user who created this user
        // Save the User entity
        userRepository.save(user);
        auditLogService.addLog(adminId,"Create User", "User", user.getUserId());
        return user;
    }
}
