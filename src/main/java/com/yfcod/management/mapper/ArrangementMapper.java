package com.yfcod.management.mapper;

import com.yfcod.management.model.Arrangement;

import java.util.List;

public interface ArrangementMapper {
    List<Arrangement> queryArrangementAll();

    List<Arrangement> queryArrangementByConditions(Arrangement arrangement);

    Arrangement queryArrangementById(Integer examId);

    void addArrangement(Arrangement arrangement);

    void updateArrangement(Arrangement arrangement);

    void deleteArrangementById(Integer examId);
}
