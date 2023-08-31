package com.softwiz.admin.service;

import com.softwiz.admin.dto.AdminRegistrationRequest;
import com.softwiz.admin.dto.UserRegistrationRequest;
import com.softwiz.admin.entity.AdminUser;
import com.softwiz.admin.entity.User;
import com.softwiz.admin.repository.AdminUserRepository;
import com.softwiz.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;
    @Autowired
    UserRepository userRepository;
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


    public User createUser (User user) {
        // Validate the input user object
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
            return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        userRepository.delete(id);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
    public User enableOrDisableUser(Long id, Boolean status) {

        //Validate the user account I'd
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        //Validate the weather status is null
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }

        //Check I'd from the repository
        User user = userRepository.getUserById(id);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        //Update the user's status
        user.setEnabled(status);

        // Save the updated user back to the repository
        userRepository.updateUser(user);

        //Return the user
        return user;
    }

}
