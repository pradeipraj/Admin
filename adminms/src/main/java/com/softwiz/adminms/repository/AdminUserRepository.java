package com.softwiz.adminms.repository;

import com.softwiz.adminms.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {

    AdminUser findByEmail(String email);
    AdminUser findByUsername(String username);

}
