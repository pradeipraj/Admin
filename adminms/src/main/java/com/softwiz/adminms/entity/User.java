package com.softwiz.adminms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
//To create table and named it "UserAccount"
@Table(name = "UserAccount")

public class User {

    //Creating user entity where table and columns are generated

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    //Create a columns and name it username
    @Column(name = "username")
    private String username;

    //Create a columns and name it email
    @Column(name = "email")
    private String email;

    //Create a columns and name it password
    @Column(name = "password")
    private String password;

    //Create a columns and name it isEnable
    @Column(name = "isEnable")
    private Boolean isEnable;

    @ManyToOne
    @JoinColumn(name = "adminId")
    private AdminUser createdByAdmin;
}
