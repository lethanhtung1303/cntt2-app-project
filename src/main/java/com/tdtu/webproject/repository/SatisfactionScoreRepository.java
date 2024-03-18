package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtSatisfactoryScore;
import com.tdtu.webproject.mybatis.condition.SatisfactionScoreCondition;

import java.util.List;

public interface SatisfactionScoreRepository {
    Long countSatisfactionScore(SatisfactionScoreCondition condition);

    List<TdtSatisfactoryScore> findSatisfactionScore(SatisfactionScoreCondition condition);

    int delete(SatisfactionScoreCondition condition);

    int create(TdtSatisfactoryScore record, String createBy);
}
