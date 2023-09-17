package com.softwiz.adminms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "Loan")

public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Book book;

    @Column(name = "issueDate")
    private LocalDate issueDate;

    @Column(name = "dueDate")
    private LocalDate dueDate;

    @Column(name = "returnDate")
    private LocalDate returnDate;

    @Column(name = "returned")
    private Boolean returned;

    @Enumerated(EnumType.STRING)
    private Status loanStatus;

    @Column(name = "lateFee")
    private Double lateFee;

    @ManyToOne
    private AdminUser adminUser;
    public enum Status{
        ONGOING,
        COMPLETED,
        CANCELLED
    }

}
