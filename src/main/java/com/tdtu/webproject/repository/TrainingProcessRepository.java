package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtTrainingProcess;
import com.tdtu.webproject.mybatis.condition.TrainingProcessCondition;

import java.math.BigDecimal;
import java.util.List;

public interface TrainingProcessRepository {
    Long countTrainingProcess(TrainingProcessCondition condition);

    List<TdtTrainingProcess> findTrainingProcess(TrainingProcessCondition condition);

    List<TdtTrainingProcess> create(TdtTrainingProcess TdtTrainingProcess);

    int update(TdtTrainingProcess TdtTrainingProcess, BigDecimal processId);

    int delete(TrainingProcessCondition condition);
}
