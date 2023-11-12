package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtQuaTrinhDaoTao;
import com.tdtu.webproject.model.condition.TrainingProcessCondition;

import java.util.List;

public interface TrainingProcessRepository {
    Long countTrainingProcess(TrainingProcessCondition condition);

    List<TdtQuaTrinhDaoTao> findTrainingProcess(TrainingProcessCondition condition);
}
