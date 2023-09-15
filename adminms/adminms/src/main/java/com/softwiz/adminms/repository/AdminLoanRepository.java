package com.softwiz.adminms.repository;

import com.softwiz.adminms.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AdminLoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByLoanStatus(Loan.Status status);
}