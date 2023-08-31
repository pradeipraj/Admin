package com.softwiz.admin.repository;

import com.softwiz.admin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);
    public  List<User> getAllUsers();
    public User getUserById(Long id);
    public void updateUser(User user);

    public void delete(Long id);

}
