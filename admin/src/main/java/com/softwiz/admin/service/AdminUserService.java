package com.softwiz.admin.service;

import com.softwiz.admin.dto.AdminRegistrationRequest;
import com.softwiz.admin.entity.AdminUser;
import com.softwiz.admin.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;
    @Autowired
    public AdminUserService(AdminUserRepository adminUserRepository){
        this.adminUserRepository = adminUserRepository;
    }
    public void registerAdminUser(AdminRegistrationRequest adminRegistrationRequest) {

        if (adminRegistrationRequest.getPassword().length() < 6){
            throw new IllegalArgumentException("Minimum 6 character required");
        }

        //Check if the email already used
        if (adminUserRepository.findByEmail(adminRegistrationRequest.getEmail()) != null){
            throw new IllegalArgumentException("Email already in use");
        }

        //Check if the username already in use
        if (adminUserRepository.findByUsername(adminRegistrationRequest.getUsername()) !=null){
            throw new IllegalArgumentException("Username already taken");
        }


        //Create a new admin user entry
        AdminUser adminUser = new AdminUser();
        adminUser.setFirstname(adminRegistrationRequest.getFirstname());
        adminUser.setLastname(adminRegistrationRequest.getLastname());
        adminUser.setRole(adminRegistrationRequest.getRole());
        adminUser.setEmail(adminRegistrationRequest.getEmail());
        adminUser.setUsername(adminRegistrationRequest.getUsername());
        adminUser.setPassword(adminRegistrationRequest.getPassword());

        //Save the user to the database
        adminUserRepository.save(adminUser);

    }
}
