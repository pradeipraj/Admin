package com.softwiz.adminms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AdminUserDTO {
    private Long adminId;
    private String email;
    private String username;
    private String password;

    @Override
    public String toString() {
        return "AdminUserDTO{" +
                "adminId=" + adminId +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
