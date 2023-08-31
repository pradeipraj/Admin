package com.softwiz.admin.controller;

import com.softwiz.admin.dto.AdminLoginRequest;
import com.softwiz.admin.dto.AdminRegistrationRequest;
import com.softwiz.admin.entity.User;
import com.softwiz.admin.service.AdminAuthService;
import com.softwiz.admin.service.AdminUserService;
import com.softwiz.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admins")

public class AdminUserController {

    private final AdminUserService adminUserService;
    private final AdminAuthService adminAuthService;
    private final UserService userService;

    @Autowired

    public AdminUserController(AdminUserService adminUserService, AdminAuthService adminAuthService, UserService userService){

        this.adminUserService = adminUserService;
        this.adminAuthService = adminAuthService;
        this.userService = userService;
    }

    //Admin user registration that returns 201 http statuses
    @PostMapping("/adminRegister")
    public ResponseEntity<?> adminRegister(@RequestBody AdminRegistrationRequest adminRegistrationRequest){

        try {
            adminUserService.registerAdminUser(adminRegistrationRequest);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    //Admin user login that returns username after successful login
    @PostMapping("/adminLogin")
    public ResponseEntity<?> adminLogin(@RequestBody AdminLoginRequest adminLoginRequest){
        try {
            String username = adminAuthService.authenticate(adminLoginRequest);
            Optional<?> user_created =Optional.ofNullable(username);
            return ResponseEntity.of(user_created);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping("/admin/createUser")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            adminUserService.createUser(user);
            return ResponseEntity.ok("User Created Successfully");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            adminUserService.deleteUser(id);
            return ResponseEntity.ok("User Deleted Successfully");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public List<User> getAllUsers() {
        return adminUserService.getAllUsers();
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<String> enableOrDisableUser(@PathVariable Long id, @RequestParam Boolean status){
    try {
        User updatedUser = adminUserService.enableOrDisableUser(id, status);
        return ResponseEntity.ok("User status updated successfully");
    } catch (Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


}
