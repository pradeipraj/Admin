package com.softwiz.adminms.controller;

import com.softwiz.adminms.dto.UserRegReq;
import com.softwiz.adminms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")

public class UserController {

    //Constructor leve User service injection into User controller
    private final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    //Register the user and try and catch it to handle exceptions
    @PostMapping("/userRegister")

    //Requesting user to register by Request body and returned will Response entity
    public ResponseEntity<?> userRegister(@RequestBody UserRegReq userRegReq){
        try {
            userService.registerUser(userRegReq);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
