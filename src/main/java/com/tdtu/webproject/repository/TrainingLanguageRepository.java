package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtTrainingLanguage;
import com.tdtu.webproject.mybatis.condition.TrainingLanguageCondition;

import java.math.BigDecimal;
import java.util.List;

public interface TrainingLanguageRepository {
    Long countAllTrainingLanguage(TrainingLanguageCondition condition);

    List<TdtTrainingLanguage> findTrainingLanguage(TrainingLanguageCondition condition);

    Long countAllTrainingLanguage();

    List<TdtTrainingLanguage> getAllTrainingLanguage();

    int create(TdtTrainingLanguage record);

    int deleteByTrainingId(BigDecimal trainingId);
}
