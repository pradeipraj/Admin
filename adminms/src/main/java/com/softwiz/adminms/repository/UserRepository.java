package com.softwiz.adminms.repository;

import com.softwiz.adminms.entity.Loan;
import com.softwiz.adminms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findById(Loan Id);
    User findByUsername(String username);
}
