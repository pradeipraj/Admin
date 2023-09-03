package com.softwiz.adminms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserRegReq {

    private String username;
    private String email;
    private String password;
    private Boolean isEnable;

    @Override
    public String toString() {
        return "UserRegReq{" +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isEnable=" + isEnable +
                '}';
    }
}
