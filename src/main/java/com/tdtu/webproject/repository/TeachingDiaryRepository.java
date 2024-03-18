package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtTeachingLog;
import com.tdtu.webproject.mybatis.condition.TeachingDiaryCondition;

import java.math.BigDecimal;
import java.util.List;

public interface TeachingDiaryRepository {
    Long countTeachingDiary(TeachingDiaryCondition condition);

    List<TdtTeachingLog> findTeachingDiary(TeachingDiaryCondition condition);

    int create(TdtTeachingLog record);

    int update(TdtTeachingLog record, BigDecimal historyId);
}
