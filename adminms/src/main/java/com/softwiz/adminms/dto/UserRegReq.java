package com.softwiz.adminms.dto;

import com.softwiz.adminms.entity.AdminUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserRegReq {

    private String username;
    private String email;
    private String password;
    private Boolean isEnable;
    private AdminUser createdByAdmin;

    @Override
    public String toString() {
        return "UserRegReq{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isEnable=" + isEnable +
                ", createdByAdmin=" + createdByAdmin +
                '}';
    }

    public Long getCreatedByAdminId() {
        return createdByAdmin.getId();
    }
}
