package com.yfcod.management.dao;

import com.yfcod.management.mapper.TimetableMapper;
import com.yfcod.management.model.Timetable;
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
public class TimetableDao {
    private static final SqlSessionFactory sqlSessionFactory;
    private static final Logger logger = Logger.getLogger(TimetableDao.class);
    private static final String adminMapperNamespace = "com.yfcod.management.mapper.TimetableMapper.";

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

    public static List<Timetable> queryTimetableAll() {
        AtomicReference<List<Timetable>> timetables = new AtomicReference<>();
        timetableMapperOperation(timetableMapper -> timetables.set(timetableMapper.queryTimetableAll()), false);
        return timetables.get();
    }

    public static List<Timetable> queryTimetableByConditions(Timetable timetable) {
        AtomicReference<List<Timetable>> timetables = new AtomicReference<>();
        timetableMapperOperation(timetableMapper ->
                timetables.set(timetableMapper.queryTimetableByConditions(timetable))
        , false);
        return timetables.get();
    }

    private interface TimetableMapperAdapter {
        void operation(TimetableMapper timetableMapper);
    }

    private static void timetableMapperOperation(TimetableMapperAdapter timetableMapperAdapter, boolean committed) {
        SqlSession session = sqlSessionFactory.openSession();
        TimetableMapper timetableMapper = session.getMapper(TimetableMapper.class);
        timetableMapperAdapter.operation(timetableMapper);
        if (committed) {
            session.commit();
        }
        session.close();
    }
}
