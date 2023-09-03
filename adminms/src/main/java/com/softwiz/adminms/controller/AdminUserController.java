package com.softwiz.adminms.controller;

import com.softwiz.adminms.dto.AdminLoginRequest;
import com.softwiz.adminms.dto.AdminUserRegReq;
import com.softwiz.adminms.entity.User;
import com.softwiz.adminms.service.AdminAuthService;
import com.softwiz.adminms.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admins")

public class AdminUserController {
    private final AdminUserService adminUserService;
    private final AdminAuthService adminAuthService;
    @Autowired
    public AdminUserController(AdminUserService adminUserService, AdminAuthService adminAuthService){
        this.adminUserService = adminUserService;
        this.adminAuthService = adminAuthService;
    }
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
            String username = adminAuthService.authenticate(adminLoginRequest);
            Optional<?> user_created =Optional.ofNullable(username);
            return ResponseEntity.of(user_created);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    //Creating user by Admin
    @PostMapping("/admin/createUser")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            adminUserService.createUser(user);
            return ResponseEntity.ok("User Created Successfully");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
