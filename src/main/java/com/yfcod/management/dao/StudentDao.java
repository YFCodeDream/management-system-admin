package com.yfcod.management.dao;

import com.yfcod.management.mapper.StudentMapper;
import com.yfcod.management.model.Student;
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
public class StudentDao {
    private static final SqlSessionFactory sqlSessionFactory;
    private static final Logger logger = Logger.getLogger(StudentDao.class);
    private static final String adminMapperNamespace = "com.yfcod.management.mapper.StudentMapper.";

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

    public static List<Student> queryStudentAll() {
        AtomicReference<List<Student>> students = new AtomicReference<>();
        studentMapperOperation(studentMapper -> students.set(studentMapper.queryStudentAll()), false);
        return students.get();
    }

    public static Student queryStudentById(String studentId) {
        AtomicReference<Student> student = new AtomicReference<>();
        studentMapperOperation(studentMapper -> student.set(studentMapper.queryStudentById(studentId)), false);
        return student.get();
    }

    public static List<Student> queryStudentByConditions(Student student) {
        AtomicReference<List<Student>> students = new AtomicReference<>();
        studentMapperOperation(studentMapper ->
                students.set(studentMapper.queryStudentByConditions(student))
        , false);
        return students.get();
    }

    private interface StudentMapperAdapter {
        void operation(StudentMapper studentMapper);
    }

    private static void studentMapperOperation(StudentMapperAdapter studentMapperAdapter, boolean committed) {
        SqlSession session = sqlSessionFactory.openSession();
        StudentMapper studentMapper = session.getMapper(StudentMapper.class);
        studentMapperAdapter.operation(studentMapper);
        if (committed) {
            session.commit();
        }
        session.close();
    }
}
