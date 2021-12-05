package com.yfcod.management.mapper;

import com.yfcod.management.model.Score;

import java.util.List;

public interface ScoreMapper {
    List<Score> queryScoreAll();

    List<Score> queryScoreByConditions(Score score);

    void addScore(Score score);

    void updateScore(Score score);

    void deleteScore(Score score);
}
