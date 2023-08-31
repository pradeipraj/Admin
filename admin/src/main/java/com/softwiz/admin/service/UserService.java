package com.softwiz.admin.service;

import com.softwiz.admin.entity.User;
import com.softwiz.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        user.setPassword((user.getPassword()));
        return userRepository.save(user);
    }
}
