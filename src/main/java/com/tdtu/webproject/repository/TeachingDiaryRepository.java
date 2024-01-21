package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtNhatKyGiangDay;
import com.tdtu.webproject.mybatis.condition.TeachingDiaryCondition;

import java.math.BigDecimal;
import java.util.List;

public interface TeachingDiaryRepository {
    Long countTeachingDiary(TeachingDiaryCondition condition);

    List<TdtNhatKyGiangDay> findTeachingDiary(TeachingDiaryCondition condition);

    int create(TdtNhatKyGiangDay record);

    int update(TdtNhatKyGiangDay record, BigDecimal historyId);
}
