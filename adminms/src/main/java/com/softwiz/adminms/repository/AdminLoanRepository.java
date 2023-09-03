package com.softwiz.adminms.repository;

import com.softwiz.adminms.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminLoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByLoanStatus(Loan.Status status);
}