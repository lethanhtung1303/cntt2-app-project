package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtTeachingHistory;
import com.tdtu.webproject.mybatis.condition.HistoryTeachingCondition;
import com.tdtu.webproject.mybatis.condition.TeachingHistoryCondition;

import java.util.List;

public interface HistoryTeachingRepository {
    Long countHistoryTeaching(HistoryTeachingCondition condition);

    List<TdtTeachingHistory> findHistoryTeaching(HistoryTeachingCondition condition);

    int create(TdtTeachingHistory record);

    int delete(TeachingHistoryCondition condition);
}
