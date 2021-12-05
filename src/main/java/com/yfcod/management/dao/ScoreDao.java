package com.yfcod.management.dao;

import com.yfcod.management.mapper.ScoreMapper;
import com.yfcod.management.model.Score;
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
public class ScoreDao {
    private static final SqlSessionFactory sqlSessionFactory;
    private static final Logger logger = Logger.getLogger(ScoreDao.class);
    private static final String adminMapperNamespace = "com.yfcod.management.mapper.ScoreMapper.";

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

    public static List<Score> queryScoreAll() {
        AtomicReference<List<Score>> scores = new AtomicReference<>();
        scoreMapperOperation(scoreMapper ->
                scores.set(scoreMapper.queryScoreAll()), false);
        return scores.get();
    }

    public static List<Score> queryScoreByConditions(Score score) {
        AtomicReference<List<Score>> scores = new AtomicReference<>();
        scoreMapperOperation(scoreMapper ->
                scores.set(scoreMapper.queryScoreByConditions(score))
        , false);
        return scores.get();
    }

    public static void addScore(Score score) {
        scoreMapperOperation(scoreMapper ->
                scoreMapper.addScore(score), true);
    }

    public static void updateScore(Score score) {
        scoreMapperOperation(scoreMapper ->
                scoreMapper.updateScore(score), true);
    }

    public static void deleteScore(Score score) {
        scoreMapperOperation(scoreMapper ->
                scoreMapper.deleteScore(score), true);
    }

    private interface ScoreMapperAdapter {
        void operation(ScoreMapper scoreMapper);
    }

    private static void scoreMapperOperation(ScoreMapperAdapter scoreMapperAdapter, boolean committed) {
        SqlSession session = sqlSessionFactory.openSession();
        ScoreMapper scoreMapper = session.getMapper(ScoreMapper.class);
        scoreMapperAdapter.operation(scoreMapper);
        if (committed) {
            session.commit();
        }
        session.close();
    }
}
