package com.softwiz.adminms.service;

import com.softwiz.adminms.dto.UserRegReq;
import com.softwiz.adminms.entity.User;
import com.softwiz.adminms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public void registerUser(UserRegReq userRegReq) {

        //Validation Logic
        //Checks the length of the password, and if it is less than eight characters, it throws an exception message
        if (userRegReq.getPassword().length() < 8){
            throw new IllegalArgumentException("Password must be minimum 8 character");
        }
        //Checks if Email already registered. Loot at UserRepository findByEmail method
        if (userRepository.findByEmail(userRegReq.getEmail()) != null){
            throw new IllegalArgumentException("Email already in use");
        }
        if (userRepository.findByUsername(userRegReq.getUsername()) != null){
            throw new IllegalArgumentException("Username already in use");
        }
        //Create a user Entry
        User user = new User();
        user.setUsername(userRegReq.getUsername());
        user.setPassword(userRegReq.getPassword());
        user.setEmail(userRegReq.getEmail());
        user.setIsEnable(userRegReq.getIsEnable());
        userRepository.save(user);


    }

}
