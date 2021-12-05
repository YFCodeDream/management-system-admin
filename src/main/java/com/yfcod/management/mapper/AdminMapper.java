package com.yfcod.management.mapper;

import com.yfcod.management.model.Admin;

import java.util.List;

public interface AdminMapper {
    List<Admin> queryAdminAll();

    Admin queryAdminById(String adminId);

    void updateAdmin(Admin admin);

    void addAdmin(Admin admin);

    void deleteAdminById(String adminId);
}
