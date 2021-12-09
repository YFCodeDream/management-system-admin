package com.yfcod.management.dao;

import com.yfcod.management.mapper.TeacherMapper;
import com.yfcod.management.model.Teacher;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings({"SameParameterValue", "unused"})
public class TeacherDao {
    private static final SqlSessionFactory sqlSessionFactory;
    private static final Logger logger = Logger.getLogger(TeacherDao.class);
    private static final String adminMapperNamespace = "com.yfcod.management.mapper.TeacherMapper.";

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

    public static List<Teacher> queryTeacherAll() {
        AtomicReference<List<Teacher>> teachers = new AtomicReference<>();
        teacherMapperOperation(teacherMapper -> teachers.set(teacherMapper.queryTeacherAll()), false);
        return teachers.get();
    }

    public static Teacher queryTeacherById(String teacherId) {
        AtomicReference<Teacher> teacher = new AtomicReference<>();
        teacherMapperOperation(teacherMapper ->
                teacher.set(teacherMapper.queryTeacherById(teacherId)), false);
        return teacher.get();
    }

    public static List<Teacher> queryTeacherByConditions(Teacher teacher) {
        AtomicReference<List<Teacher>> teachers = new AtomicReference<>();
        teacherMapperOperation(teacherMapper ->
                teachers.set(teacherMapper.queryTeacherByConditions(teacher))
        , false);
        return teachers.get();
    }

    public static void updateTeacherPwd(Teacher teacher) {
        teacherMapperOperation(teacherMapper ->
                teacherMapper.updateTeacherPwd(teacher), true);
    }

    private interface TeacherMapperAdapter {
        void operation(TeacherMapper teacherMapper);
    }

    private static void teacherMapperOperation(TeacherMapperAdapter teacherMapperAdapter, boolean committed) {
        SqlSession session = sqlSessionFactory.openSession();
        TeacherMapper teacherMapper = session.getMapper(TeacherMapper.class);
        teacherMapperAdapter.operation(teacherMapper);
        if (committed) {
            session.commit();
        }
        session.close();
    }
}
