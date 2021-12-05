package com.yfcod.management.dao;

import com.yfcod.management.mapper.CourseMapper;
import com.yfcod.management.model.Course;
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
public class CourseDao {
    // 全局公用一个sqlSessionFactory
    private static final SqlSessionFactory sqlSessionFactory;
    private static final Logger logger = Logger.getLogger(CourseDao.class);
    private static final String adminMapperNamespace = "com.yfcod.management.mapper.CourseMapper.";

    // 由静态代码块实例化SqlSessionFactory
    static {
        InputStream configInputStream = null;
        try {
            // 注意是org.apache.ibatis.io.Resources
            configInputStream = Resources.getResourceAsStream("com/yfcod/management/mybatis-config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 由SqlSessionFactoryBuilder构建sqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configInputStream);
        // 关闭配置文件输入流
        if (configInputStream != null) {
            try {
                configInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Course> queryCourseAll() {
        AtomicReference<List<Course>> courses = new AtomicReference<>();
        courseMapperOperation(courseMapper -> {
            assert false;
            courses.set(courseMapper.queryCourseAll());
        }, false);
        return courses.get();
    }

    public static List<Course> queryCourseByConditions(Course course) {
        AtomicReference<List<Course>> courses = new AtomicReference<>();
        courseMapperOperation(courseMapper ->
                courses.set(courseMapper.queryCourseByConditions(course))
        , false);
        return courses.get();
    }

    private interface CourseMapperAdapter {
        void operation(CourseMapper courseMapper);
    }

    private static void courseMapperOperation(CourseMapperAdapter courseMapperAdapter, boolean committed) {
        SqlSession session = sqlSessionFactory.openSession();
        CourseMapper courseMapper = session.getMapper(CourseMapper.class);
        courseMapperAdapter.operation(courseMapper);
        if (committed) {
            session.commit();
        }
        session.close();
    }
}
