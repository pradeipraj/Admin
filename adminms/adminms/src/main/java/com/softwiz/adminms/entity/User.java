package com.softwiz.adminms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "UserTable")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String password;
    private Boolean isEnable;

    @ManyToOne
    @JoinColumn(name = "adminId")
    private AdminUser createdByAdmin;
}
