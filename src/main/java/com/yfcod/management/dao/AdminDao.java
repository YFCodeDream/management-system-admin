package com.yfcod.management.dao;

import com.yfcod.management.mapper.AdminMapper;
import com.yfcod.management.model.Admin;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("unused")
public class AdminDao {
    private static final SqlSessionFactory sqlSessionFactory;
    private static final Logger logger = Logger.getLogger(AdminDao.class);
    private static final String adminMapperNamespace = "com.yfcod.management.mapper.AdminMapper.";

    static {
        InputStream configInputStream = null;
        try {
            configInputStream = Resources.getResourceAsStream("com/yfcod/management/mybatis-config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configInputStream);
        if (configInputStream != null) {
            try {
                configInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Admin> queryAdminAll() {
        AtomicReference<List<Admin>> admins = new AtomicReference<>();
        adminMapperOperation(adminMapper -> {
            assert false;
            admins.set(adminMapper.queryAdminAll());
        }, false);
        return admins.get();
    }

    public static Admin queryAdminById(String adminId) {
        AtomicReference<Admin> admin = new AtomicReference<>();
        adminMapperOperation(adminMapper -> {
            assert false;
            admin.set(adminMapper.queryAdminById(adminId));
        }, false);
        return admin.get();
    }

    public static void addAdmin(Admin admin) {
        adminMapperOperation(adminMapper -> adminMapper.addAdmin(admin), true);
    }

    public static void updateAdmin(Admin admin) {
        adminMapperOperation(adminMapper -> adminMapper.updateAdmin(admin), true);
    }

    private interface AdminMapperAdapter {
        void operation(AdminMapper adminMapper);
    }

    private static void adminMapperOperation(AdminMapperAdapter adminMapperAdapter, boolean committed) {
        SqlSession session = sqlSessionFactory.openSession();
        AdminMapper adminMapper = session.getMapper(AdminMapper.class);
        adminMapperAdapter.operation(adminMapper);
        if (committed) {
            session.commit();
        }
        session.close();
    }
}
