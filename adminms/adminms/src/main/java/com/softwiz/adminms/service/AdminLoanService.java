package com.softwiz.adminms.service;

import com.softwiz.adminms.entity.AdminUser;
import com.softwiz.adminms.entity.Loan;
import com.softwiz.adminms.repository.AdminLoanRepository;
import com.softwiz.adminms.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminLoanService {
    @Autowired
    private AdminLoanRepository adminLoanRepository;
    @Autowired
    private AdminUserRepository adminUserRepository;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private AuditLogService auditLogService;

    public List<Loan> getOngoingLoansByAdmin(AdminUser admin) {
        return adminLoanRepository.findByAdminUserAndLoanStatus(admin, Loan.Status.ONGOING);
    }
    public List<Loan> getCompletedLoansByAdmin(AdminUser admin) {
        return adminLoanRepository.findByAdminUserAndLoanStatus(admin, Loan.Status.COMPLETED);
    }
    public String updateLoanStatusByAdmin(Long adminId, Long loanId, Loan.Status newStatus) {
        Optional<AdminUser> optionalAdmin = adminUserRepository.findById(adminId);
        Optional<Loan> optionalLoan = adminLoanRepository.findById(loanId);

        if (optionalAdmin.isPresent() && optionalLoan.isPresent()) {
            Loan loan = optionalLoan.get();
            // Update the loan status
            loan.setLoanStatus(newStatus);
            adminLoanRepository.save(loan);
            auditLogService.addLog(adminId, "Update Loan", "Loan", loanId);
            return "Loan status updated successfully.";
        } else if (optionalAdmin.isEmpty()) {
            return "Admin not found.";
        } else {
            return "Loan not found.";
        }
    }
    public List<Loan> getAllLoansByAdmin(AdminUser admin) {
        return adminLoanRepository.findAll();
    }
    public List<Loan> getLateFeeLoansByAdmin(AdminUser admin) {
        return adminLoanRepository.findByAdminUserAndLateFeeGreaterThan(admin, 0.0);
    }
    public List<Loan> getCancelledLoansByAdmin(AdminUser admin) {
        return adminLoanRepository.findByAdminUserAndLoanStatus(admin, Loan.Status.CANCELLED);
    }
}

