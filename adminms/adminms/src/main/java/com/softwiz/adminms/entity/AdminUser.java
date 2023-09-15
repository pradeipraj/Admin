package com.softwiz.adminms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "AdminUser")

public class AdminUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long adminId;
    private String email;
    private String username;
    private String password;
}
