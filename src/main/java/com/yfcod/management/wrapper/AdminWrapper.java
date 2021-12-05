package com.yfcod.management.wrapper;

import com.yfcod.management.model.Admin;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;

@Data
public class AdminWrapper {
    private StringProperty adminIdProperty;
    private StringProperty adminPwdProperty;
    private StringProperty adminPhoneProperty;

    public AdminWrapper(Admin admin) {
        this.adminIdProperty = new SimpleStringProperty(admin.getAdminId());
        this.adminPhoneProperty = new SimpleStringProperty(admin.getAdminPhone());
        this.adminPhoneProperty = new SimpleStringProperty(admin.getAdminPwd());
    }
}
