package com.softwiz.adminms.controller;

import com.softwiz.adminms.entity.AdminUser;
import com.softwiz.adminms.entity.Loan;
import com.softwiz.adminms.service.AdminLoanService;
import com.softwiz.adminms.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/loans")

public class AdminLoanController {
    @Autowired
    private AdminLoanService adminLoanService;
    @Autowired
    private AdminUserService adminUserService;
    //Get OnGoing Loans
    @GetMapping("/ongoing/{adminId}")
    public ResponseEntity<?> getOngoingLoansForAdmin(@PathVariable Long adminId) {
        AdminUser admin = adminUserService.findAdminById(adminId);

        if (admin != null) {
            List<Loan> ongoingLoans = adminLoanService.getOngoingLoansByAdmin(admin);

            if (!ongoingLoans.isEmpty()) {
                return ResponseEntity.ok(ongoingLoans);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No OnGoing Loans found in the database.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found.");
        }
    }
    @GetMapping("/completed/{adminId}")
    public ResponseEntity<?> getCompletedLoansForAdmin(@PathVariable Long adminId) {
        AdminUser admin = adminUserService.findAdminById(adminId);

        if (admin != null) {
            List<Loan> completedLoans = adminLoanService.getCompletedLoansByAdmin(admin);

            if (!completedLoans.isEmpty()) {
                return ResponseEntity.ok(completedLoans);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Completed Loans found in the database.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found.");
        }
    }
    //Change the loan status by Admin
    @PutMapping("/{adminId}/{loanId}/update-status/{newStatus}")
    public ResponseEntity<String> updateLoanStatusByAdmin(@PathVariable Long adminId, @PathVariable Long loanId,
            @PathVariable Loan.Status newStatus) {

        String result = adminLoanService.updateLoanStatusByAdmin(adminId, loanId, newStatus);
        if (result.equals("Loan not found.") || result.equals("Admin not found.")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else if (result.equals("Loan status updated successfully.")) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }
    //Get all loan details by Admin
    @GetMapping("/allLoans/{adminId}")
    public ResponseEntity<?> getAllLoansByAdmin(@PathVariable Long adminId) {
        AdminUser admin = adminUserService.findAdminById(adminId);

        if (admin != null) {
            List<Loan> loans = adminLoanService.getAllLoansByAdmin(admin);

            if (!loans.isEmpty()) {
                return ResponseEntity.ok(loans);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan Database is Empty.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found.");
        }
    }
    //Late Fee Loan Details
    @GetMapping("/late-fee/{adminId}")
    public ResponseEntity<?> getLateFeeLoansByAdmin(@PathVariable Long adminId) {
        AdminUser admin = adminUserService.findAdminById(adminId);

        if (admin != null) {
            List<Loan> lateFeeLoans = adminLoanService.getLateFeeLoansByAdmin(admin);

            if (!lateFeeLoans.isEmpty()) {
                return ResponseEntity.ok(lateFeeLoans);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Late Fee loan Details found in the database.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found.");
        }
    }
    //Get cancelled loans
    @GetMapping("/cancelledLoans/{adminId}")
    public ResponseEntity<?> getCancelledLoansForAdmin(@PathVariable Long adminId) {
        AdminUser admin = adminUserService.findAdminById(adminId);
        if (admin != null) {
            List<Loan> cancelledLoans = adminLoanService.getCancelledLoansByAdmin(admin);
            if (!cancelledLoans.isEmpty()) {
                return ResponseEntity.ok(cancelledLoans);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cancelled loans not found in the database.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found.");
        }
    }
}
