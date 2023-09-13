package com.softwiz.adminms.controller;

import com.softwiz.adminms.dto.AdminUserDTO;
import com.softwiz.adminms.dto.UserDTO;
import com.softwiz.adminms.entity.AdminUser;
import com.softwiz.adminms.entity.User;
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
    @PostMapping("/createAdmin")
    public ResponseEntity<String> createAdmin(@RequestBody AdminUserDTO adminUserDTO) {
        AdminUser createdAdminUser = adminUserService.createAdminUser(adminUserDTO);
        if (createdAdminUser != null) {
            return new ResponseEntity<>("AdminUser created successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to create AdminUser", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PostMapping("/createUserByAdmin")
    public ResponseEntity<User> createUserByAdmin(@RequestBody UserDTO userDTO){

        try {
            Long adminId = userDTO.getAdminId();

            if (adminId == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            User createdUser = adminUserService.createUserByAdmin(userDTO, adminId);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}