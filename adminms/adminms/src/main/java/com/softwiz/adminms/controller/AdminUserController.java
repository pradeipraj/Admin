package com.softwiz.adminms.controller;

import com.softwiz.adminms.dto.AdminLoginRequest;
import com.softwiz.adminms.dto.AdminUserDTO;
import com.softwiz.adminms.dto.UserDTO;
import com.softwiz.adminms.dto.UserLoginRequest;
import com.softwiz.adminms.entity.AdminUser;
import com.softwiz.adminms.entity.User;
import com.softwiz.adminms.service.AdminAuthService;
import com.softwiz.adminms.service.AdminUserService;
import com.softwiz.adminms.service.AuditLogService;
import com.softwiz.adminms.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/admins")
public class AdminUserController {
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private AdminAuthService adminAuthService;
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private AuditLogService auditLogService;

    //Creating AdminUser
    @PostMapping("/createAdmin")
    public ResponseEntity<String> createAdmin(@RequestBody AdminUserDTO adminUserDTO) {
        try {
            AdminUser createdAdminUser = adminUserService.createAdminUser(adminUserDTO);
            if (createdAdminUser != null) {
                return new ResponseEntity<>("AdminUser created successfully", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Failed to create AdminUser", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    //Admin Login
    @PostMapping("/adminLogin")
    public ResponseEntity<String> adminLogin(@RequestBody AdminLoginRequest adminLoginRequest) {
        if (adminAuthService.authenticate(adminLoginRequest)) {
            return ResponseEntity.ok("Admin login successful");
        } else {
            return new ResponseEntity<>("Incorrect email or password", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/createUserByAdmin")
    public ResponseEntity<?> createUserByAdmin(@RequestBody UserDTO userDTO) {

        try {
            Long adminId = userDTO.getAdminId();

            if (adminId == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            User createdUser = adminUserService.createUserByAdmin(userDTO, adminId);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/userLogin")
    public ResponseEntity<String> userLogin(@RequestBody UserLoginRequest userLoginRequest) {
        if (userAuthService.authenticate(userLoginRequest)) {
            return ResponseEntity.ok("User login successful");
        } else {
            return new ResponseEntity<>("Incorrect email or password", HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/deleteUserByAdmin/{adminId}/{userId}")
    public ResponseEntity<String> deleteUserByAdmin(@PathVariable Long adminId, @PathVariable Long userId) {
        try {
            // Check if the admin exists
            AdminUser adminUser = adminUserService.findAdminById(adminId);
            if (adminUser == null) {
                return new ResponseEntity<>("Admin not found", HttpStatus.NOT_FOUND);
            }
            // Check if the user exists
            Optional<User> user = adminUserService.findUserById(userId);
            if (user.isEmpty()) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
            // Perform user deletion
            adminUserService.deleteUserByAdmin(userId);
            auditLogService.addLog(adminId, "Delete User", "User", userId);
            return ResponseEntity.ok("User deleted successfully");
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateUserStatusByAdmin")
    public ResponseEntity<String> updateUserStatusByAdmin(@RequestBody UserDTO userDTO) {
        try {
            Long adminId = userDTO.getAdminId();

            if (adminId == null) {
                return new ResponseEntity<>("Admin ID is required", HttpStatus.BAD_REQUEST);
            }
            // Check if the admin exists
            AdminUser adminUser = adminUserService.findAdminById(adminId);
            if (adminUser == null) {
                return new ResponseEntity<>("Admin not found", HttpStatus.NOT_FOUND);
            }
            Long userId = userDTO.getUserId();
            if (userId == null) {
                return new ResponseEntity<>("User ID is required", HttpStatus.BAD_REQUEST);
            }
            // Check if the user exists
            Optional<User> existingUser = adminUserService.findUserById(userId);
            if (existingUser.isEmpty()) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
            boolean newStatus = userDTO.getIsEnable();
            // Update user status
            adminUserService.updateUserStatusByAdmin(userId, newStatus);

            String action = newStatus ? "enabled" : "disabled";
            auditLogService.addLog(adminId, "Update User Status", "User", userId);
            return ResponseEntity.ok("User account " + action + " successfully");
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = adminUserService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("No users found in the database", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}