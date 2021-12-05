package com.yfcod.management.model;

import lombok.Data;

@Data
public class Admin {
    private String adminId;
    private String adminPwd;
    private String adminPhone;

    public Admin(String adminId, String adminPwd, String adminPhone) {
        this.adminId = adminId;
        this.adminPwd = adminPwd;
        this.adminPhone = adminPhone;
    }
}
