package com.softwiz.adminms.service;

import com.softwiz.adminms.entity.Loan;
import com.softwiz.adminms.repository.AdminLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminLoanService {
    @Autowired
    private AdminLoanRepository adminLoanRepository;
    public List<Loan> getAllOngoingLoans() {
        return adminLoanRepository.findByLoanStatus(Loan.Status.ONGOING);
    }
    public List<Loan> getAllPastLoans() {
        return adminLoanRepository.findByLoanStatus(Loan.Status.COMPLETED);
    }
    public void updateLoanStatus(Long loanId, Loan.Status newStatus) {
        Optional<Loan> optionalLoan = adminLoanRepository.findById(loanId);
        if (optionalLoan.isPresent()) {
            Loan loan = optionalLoan.get();
            loan.setLoanStatus(newStatus);
            adminLoanRepository.save(loan);
        } else {
            throw new IllegalArgumentException("Loan not found. The ID is: " + loanId);
        }
    }
}

