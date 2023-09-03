package com.softwiz.adminms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AdminUserRegReq {

    private String email;
    private String username;
    private String password;

    @Override
    public String toString() {
        return "AdminRegReq{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
