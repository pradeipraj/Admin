package com.softwiz.adminms.repository;

import com.softwiz.adminms.entity.AdminUser;
import com.softwiz.adminms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {

    AdminUser findByEmail(String email);
    AdminUser findByUsername(String username);
}
