package com.softwiz.adminms.dto;

import com.softwiz.adminms.entity.Loan;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class LoanDTO {

    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private Boolean returned;
    private Loan.Status loanStatus;
    private Double lateFee;

    @Override
    public String toString() {
        return "LoanDTO{" +
                "issueDate=" + issueDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                ", returned=" + returned +
                ", loanStatus=" + loanStatus +
                ", lateFee=" + lateFee +
                '}';
    }
}

