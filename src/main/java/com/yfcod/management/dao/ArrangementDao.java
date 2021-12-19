package com.yfcod.management.dao;

import com.yfcod.management.mapper.ArrangementMapper;
import com.yfcod.management.model.Arrangement;
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
public class ArrangementDao {
    private static final SqlSessionFactory sqlSessionFactory;
    private static final Logger logger = Logger.getLogger(ArrangementDao.class);
    private static final String arrangementMapperNamespace = "com.yfcod.management.mapper.ArrangementMapper.";

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

    public static List<Arrangement> queryArrangementAll() {
        AtomicReference<List<Arrangement>> arrangements = new AtomicReference<>();
        arrangementMapperOperation(arrangementMapper -> {
            assert false;
            arrangements.set(arrangementMapper.queryArrangementAll());
        }, false);
        return arrangements.get();
    }

    public static List<Arrangement> queryArrangementByConditions(Arrangement arrangement) {
        AtomicReference<List<Arrangement>> arrangements = new AtomicReference<>();
        arrangementMapperOperation(arrangementMapper ->
                arrangements.set(arrangementMapper.queryArrangementByConditions(arrangement)), false);
        return arrangements.get();
    }

    public static Arrangement queryArrangementById(Integer examId) {
        AtomicReference<Arrangement> arrangement = new AtomicReference<>();
        arrangementMapperOperation(arrangementMapper ->
                arrangement.set(arrangementMapper.queryArrangementById(examId)), false);
        return arrangement.get();
    }

    public static void addArrangement(Arrangement arrangement) {
        arrangementMapperOperation(arrangementMapper ->
                arrangementMapper.addArrangement(arrangement), true);
    }

    public static void updateArrangement(Arrangement arrangement) {
        arrangementMapperOperation(arrangementMapper ->
                arrangementMapper.updateArrangement(arrangement), true);
    }

    public static void deleteArrangement(Arrangement arrangement) {
        arrangementMapperOperation(arrangementMapper ->
                arrangementMapper.deleteArrangementById(arrangement.getExamId()), true);
    }

    private interface ArrangementMapperAdapter {
        void operation(ArrangementMapper arrangementMapper);
    }

    private static void arrangementMapperOperation(ArrangementMapperAdapter arrangementMapperAdapter, boolean committed) {
        SqlSession session = sqlSessionFactory.openSession();
        ArrangementMapper arrangementMapper = session.getMapper(ArrangementMapper.class);
        arrangementMapperAdapter.operation(arrangementMapper);
        if (committed) {
            session.commit();
        }
        session.close();
    }
}
