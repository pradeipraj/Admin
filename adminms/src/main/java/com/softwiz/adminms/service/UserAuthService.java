package com.softwiz.adminms.service;

import com.softwiz.adminms.dto.UserLoginRequest;
import com.softwiz.adminms.entity.User;
import com.softwiz.adminms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {
    @Autowired
    UserRepository userRepository;
    public String authenticate(UserLoginRequest userLoginRequest) {

        User user = userRepository.findByEmail(userLoginRequest.getEmail());
        return user.getUsername();

    }
}
