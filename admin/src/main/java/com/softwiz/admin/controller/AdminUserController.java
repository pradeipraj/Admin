package com.softwiz.admin.controller;

import com.softwiz.admin.dto.AdminLoginRequest;
import com.softwiz.admin.dto.AdminRegistrationRequest;
import com.softwiz.admin.service.AdminAuthService;
import com.softwiz.admin.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admins")

public class AdminUserController {

    private final AdminUserService adminUserService;
    private final AdminAuthService adminAuthService;

    @Autowired
    public AdminUserController(AdminUserService adminUserService, AdminAuthService adminAuthService){
        this.adminUserService = adminUserService;
        this.adminAuthService = adminAuthService;
    }

    //Admin user registration that returns 201 http status
    @PostMapping("/admin_register")
    public ResponseEntity<?> admin_register(@RequestBody AdminRegistrationRequest adminRegistrationRequest){

        try {
            adminUserService.registerAdminUser(adminRegistrationRequest);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }

    }

    //Admin user login that returns username after successful login
    @PostMapping("/admin_login")
    public ResponseEntity<?> admin_login(@RequestBody AdminLoginRequest adminLoginRequest){
        try {
            String username = adminAuthService.authenticate(adminLoginRequest);
            Optional<?> user_created =Optional.ofNullable(username);
            return ResponseEntity.of(user_created);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }


    }


}
