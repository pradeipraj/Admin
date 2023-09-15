package com.softwiz.adminms.controller;


import com.softwiz.adminms.entity.Loan;
import com.softwiz.adminms.service.AdminLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")

public class AdminLoanController {

    @Autowired
    private AdminLoanService adminLoanService;

    //Admin is able to get ongoing loan details
    @GetMapping("/ongoing")
    public List<Loan> getAllOngoingLoans(){
        return adminLoanService.getAllOngoingLoans();
    }

    //Admin is able to get completed loan details
    @GetMapping("/completed")
    public List<Loan> getAllPastLoans(){
        return adminLoanService.getAllPastLoans();
    }

    //Admin is able to update the loan status manually
    @PutMapping("/{id}/status")
    public void updateLoanStatus(@PathVariable Long id, @RequestBody Loan.Status newStatus) {
        adminLoanService.updateLoanStatus(id, newStatus);
    }
}
