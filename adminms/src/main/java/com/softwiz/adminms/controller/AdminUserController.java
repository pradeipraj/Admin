package com.softwiz.adminms.controller;

import com.softwiz.adminms.dto.AdminLoginRequest;
import com.softwiz.adminms.dto.AdminUserRegReq;
import com.softwiz.adminms.dto.UserRegReq;
import com.softwiz.adminms.entity.AdminUser;
import com.softwiz.adminms.entity.User;
import com.softwiz.adminms.repository.AdminUserRepository;
import com.softwiz.adminms.service.AdminAuthService;
import com.softwiz.adminms.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admins")

public class AdminUserController {
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private AdminAuthService adminAuthService;
    @Autowired
    private AdminUserRepository adminUserRepository;

    @PostMapping("/adminRegister")
    public ResponseEntity<?> adminRegister(@RequestBody AdminUserRegReq adminUserRegReq){
        try {
            adminUserService.registerAdminUser(adminUserRegReq);
            return new ResponseEntity<>(HttpStatus.CREATED);
            }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping("/adminLogin")
    public ResponseEntity<?> adminLogin(@RequestBody AdminLoginRequest adminLoginRequest){
        try {
            adminAuthService.authenticate(adminLoginRequest);
            return ResponseEntity.ok("Admin login successful");
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
    //Creating user by Admin
    @PostMapping("/admin/createUser")
    public ResponseEntity<String> createUser(@RequestBody User user, Long adminId) {
        try {
            adminUserService.createUser(user, adminId);
            user.setCreatedByAdmin(new AdminUser());
            return ResponseEntity.ok("User Created Successfully");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
