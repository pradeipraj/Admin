package com.softwiz.adminms.controller;

import com.softwiz.adminms.dto.AdminLoginRequest;
import com.softwiz.adminms.dto.UserLoginRequest;
import com.softwiz.adminms.dto.UserRegReq;
import com.softwiz.adminms.service.UserAuthService;
import com.softwiz.adminms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")

public class UserController {

    //Constructor leve User service injection into User controller
    private final UserService userService;
    private final UserAuthService userAuthService;
    @Autowired
    public UserController(UserService userService, UserAuthService userAuthService){
        this.userService = userService;
        this.userAuthService = userAuthService;
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

    /*@PostMapping("/userLogin")
    public ResponseEntity<?> userLogin(@RequestBody UserLoginRequest userLoginRequest){
        try {
            String username = userAuthService.authenticate(userLoginRequest);
            Optional<?> user_created =Optional.ofNullable(username);
            return ResponseEntity.of(user_created);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }*/

    @PostMapping("/userLogin")
    public ResponseEntity<?> userLogin(@RequestBody UserLoginRequest userLoginRequest){
        try {
            userAuthService.authenticate(userLoginRequest);
            return ResponseEntity.ok("User login successful");
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
