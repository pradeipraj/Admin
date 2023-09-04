package com.softwiz.adminms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserLoginRequest {
    private String email;
    private String password;

    @Override
    public String toString() {
        return "UserLoginRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
