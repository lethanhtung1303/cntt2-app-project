package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtDiemHaiLong;
import com.tdtu.webproject.model.condition.SatisfactionScoreCondition;

import java.util.List;

public interface SatisfactionScoreRepository {
    Long countSatisfactionScore(SatisfactionScoreCondition condition);

    List<TdtDiemHaiLong> findSatisfactionScore(SatisfactionScoreCondition condition);
}
