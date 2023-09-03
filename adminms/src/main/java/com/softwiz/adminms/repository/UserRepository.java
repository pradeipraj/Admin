package com.softwiz.adminms.repository;

import com.softwiz.adminms.entity.Loan;
import com.softwiz.adminms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findById(Loan Id);
    User findByUsername(String username);
}
