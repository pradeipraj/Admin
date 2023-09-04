package com.softwiz.adminms.service;

import com.softwiz.adminms.dto.UserRegReq;
import com.softwiz.adminms.entity.User;
import com.softwiz.adminms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public void registerUser(UserRegReq userRegReq) {
        /*Validate an email pattern: E mail id must have alphabets between A-Z, a-z. Email must
        contain numbers between 0-9 and some special symbol.*/
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        String email = userRegReq.getEmail();
        //Check if it follows the pattern, if not that returns invalid email address.
        if (!Pattern.matches(emailPattern, email)) {
            throw new IllegalArgumentException("Invalid email address");
        }
        //Checks if Email already registered. Look at UserRepository findByEmail method
        if (userRepository.findByEmail(userRegReq.getEmail()) != null){
            throw new IllegalArgumentException("E-mail already in use");
        }
        if (userRepository.findByUsername(userRegReq.getUsername()) != null){
            throw new IllegalArgumentException("Username already in use");
        }
        //Checks the length of the password, and if it is less than eight characters, it throws an exception message
        if (userRegReq.getPassword().length() < 8){
            throw new IllegalArgumentException("Password must be minimum 8 character");
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
