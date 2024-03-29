package com.yfcod.management.controller;

public interface MenuItemOperation {
    void handleShowAllTableData();

    void handleExportCurrentData();

    void handleExportAllData();

    void handleCurrentSendMail();

    void handleAllSendMail();

    void handleUpdateInfo();

    void handleLogout();

    void handleQuit();
}
