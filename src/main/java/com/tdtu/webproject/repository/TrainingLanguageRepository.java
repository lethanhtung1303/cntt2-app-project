package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtNgonNguDaoTao;
import com.tdtu.webproject.model.condition.TrainingLanguageCondition;

import java.util.List;

public interface TrainingLanguageRepository {
    Long countAllTrainingLanguage(TrainingLanguageCondition condition);

    List<TdtNgonNguDaoTao> findTrainingLanguage(TrainingLanguageCondition condition);

    Long countAllTrainingLanguage();

    List<TdtNgonNguDaoTao> getAllTrainingLanguage();
}
