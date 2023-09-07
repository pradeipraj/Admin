package com.softwiz.adminms.service;

import com.softwiz.adminms.dto.AdminUserRegReq;
import com.softwiz.adminms.entity.AdminUser;
import com.softwiz.adminms.entity.User;
import com.softwiz.adminms.repository.AdminUserRepository;
import com.softwiz.adminms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;
    private final UserRepository userRepository;
    @Autowired
    public AdminUserService(AdminUserRepository adminUserRepository, UserRepository userRepository){
        this.adminUserRepository = adminUserRepository;
        this.userRepository = userRepository;
    }
    public void registerAdminUser(AdminUserRegReq adminUserRegReq) {

        if (adminUserRegReq.getPassword().length() < 8){
            throw new IllegalArgumentException("Minimum 8 character required");
        }

        //Check if the email already used
        if (adminUserRepository.findByEmail(adminUserRegReq.getEmail()) != null){
            throw new IllegalArgumentException("Email already in use");
        }

        //Check if the username already in use
        if (adminUserRepository.findByUsername(adminUserRegReq.getUsername()) !=null){
            throw new IllegalArgumentException("Username already taken");
        }

        //Create and admin user entry
        AdminUser adminUser = new AdminUser();
        adminUser.setEmail(adminUserRegReq.getEmail());
        adminUser.setUsername(adminUserRegReq.getUsername());
        adminUser.setPassword(adminUserRegReq.getPassword());

        //Save the user to the database
        adminUserRepository.save(adminUser);

    }
    //Validate before Creating user by admin
    public User createUser(User user, Long adminId) {
        // Validate the input user object
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        return userRepository.save(user);
    }

}
