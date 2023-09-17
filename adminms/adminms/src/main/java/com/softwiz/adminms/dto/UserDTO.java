package com.softwiz.adminms.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserDTO {
    private String username;
    private String password;
    private Boolean isEnable;
    private Long adminId;
    private Long userId;

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isEnable=" + isEnable +
                ", adminId=" + adminId +
                ", userId=" + userId +
                '}';
    }
}
