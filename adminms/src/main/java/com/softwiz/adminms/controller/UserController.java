package com.softwiz.adminms.controller;

import com.softwiz.adminms.dto.UserLoginRequest;
import com.softwiz.adminms.dto.UserRegReq;
import com.softwiz.adminms.entity.User;
import com.softwiz.adminms.service.UserAuthService;
import com.softwiz.adminms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/users")

public class UserController {
    //Constructor level User service injection into User controller
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
            userService.registerUser(userRegReq);
            return new ResponseEntity<>("Registration successfull", HttpStatus.CREATED);
    }
    @PostMapping("/userLogin")
    public ResponseEntity<?> userLogin(@RequestBody UserLoginRequest userLoginRequest){
        try {
            userAuthService.authenticate(userLoginRequest);
            return ResponseEntity.ok("User login successful");
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long userId){
        try {
            Optional<User> user = userService.getUserById(userId);
            if (user.isPresent()){
                userService.deleteUser(userId);
                return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
            }else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
