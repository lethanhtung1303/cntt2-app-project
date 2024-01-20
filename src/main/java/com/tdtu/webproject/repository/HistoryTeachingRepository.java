package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtLichSuGiangDay;
import com.tdtu.webproject.mybatis.condition.HistoryTeachingCondition;
import com.tdtu.webproject.mybatis.condition.TeachingHistoryCondition;

import java.util.List;

public interface HistoryTeachingRepository {
    Long countHistoryTeaching(HistoryTeachingCondition condition);

    List<TdtLichSuGiangDay> findHistoryTeaching(HistoryTeachingCondition condition);

    int create(TdtLichSuGiangDay record);

    int delete(TeachingHistoryCondition condition);
}
