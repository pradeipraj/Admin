package com.softwiz.adminms.service;

import com.softwiz.adminms.dto.AdminUserDTO;
import com.softwiz.adminms.dto.UserDTO;
import com.softwiz.adminms.entity.AdminUser;
import com.softwiz.adminms.entity.Book;
import com.softwiz.adminms.entity.User;
import com.softwiz.adminms.repository.AdminUserRepository;
import com.softwiz.adminms.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AdminUserService {
    @Autowired
    private AdminUserRepository adminUserRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuditLogService auditLogService;
    public AdminUser createAdminUser(AdminUserDTO adminUserDTO) {
        //E-mail pattern matching
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern emailPatternReg = Pattern.compile(emailPattern);
        Matcher emailMatcherReg = emailPatternReg.matcher(adminUserDTO.getEmail());
        if (!emailMatcherReg.matches()) {
            throw new IllegalArgumentException("Invalid email address format");
        }
        //Checks whether E-mail is already in use
        if (adminUserRepository.findByEmail(adminUserDTO.getEmail()) != null){
            throw new IllegalArgumentException("E-mail already in use");
        }
        //Checks whether Username already in use
        if (adminUserRepository.findByUsername(adminUserDTO.getUsername()) != null){
            throw new IllegalArgumentException("Username already in use");
        }
        //Password patter matching
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        Pattern passwordPatternReg = Pattern.compile(passwordPattern);
        Matcher passwordMatcher = passwordPatternReg.matcher(adminUserDTO.getPassword());

        if (!passwordMatcher.matches()) {
            // The provided password does not match the pattern
            throw new IllegalArgumentException("Invalid password format. " +
                    "Password must be at least 8 characters long and " +
                    "contain at least one uppercase letter, " +
                    "one lowercase letter, " +
                    "one digit, and " +
                    "one special character (@#$%^&+=!).");
        }
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

        //Checks whether Username already in use
        if (userRepository.findByUsername(userDTO.getUsername()) != null){
            throw new IllegalArgumentException("Username already in use");
        }
        //Password patter matching
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        Pattern passwordPatternReg = Pattern.compile(passwordPattern);
        Matcher passwordMatcher = passwordPatternReg.matcher(userDTO.getPassword());

        if (!passwordMatcher.matches()) {
            // The provided password does not match the pattern
            throw new IllegalArgumentException("Invalid password format." + "\n"+
                    "Password must be at least 8 characters long and "+ "\n" +
                    "contain at least one uppercase letter, " +"\n" +
                    "one lowercase letter, " +"\n" +
                    "one digit, and "+"\n" +
                    "one special character (@#$%^&+=!).");
        }
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

    public void deleteUserByAdmin(Long userId) {
        // Check if the user exists
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found with ID: " + userId);
        }
    }

    public AdminUser findAdminById(Long adminId) {
        // Check if the admin exists
        Optional<AdminUser> adminUser = adminUserRepository.findById(adminId);
        return adminUser.orElse(null); // Returns null if admin is not found
    }

    public Optional<User> findUserById(Long userId) {
        // You can simply return the Optional<User> here
        return userRepository.findById(userId);
    }

    public void updateUserStatusByAdmin(Long userId, boolean newStatus) {
        // Check if the user exists
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIsEnable(newStatus); // Set the new status
            // Update the user in the database
            userRepository.save(user);
        } else {
            throw new EntityNotFoundException("User not found with ID: " + userId);
        }
    }
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new NoSuchElementException("No users found in the database");
        }
        return users;
    }
}
