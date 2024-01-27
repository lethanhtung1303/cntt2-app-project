package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtNgonNguDaoTao;
import com.tdtu.webproject.mybatis.condition.TrainingLanguageCondition;

import java.math.BigDecimal;
import java.util.List;

public interface TrainingLanguageRepository {
    Long countAllNgonNguDaoTao(TrainingLanguageCondition condition);

    List<TdtNgonNguDaoTao> findNgonNguDaoTao(TrainingLanguageCondition condition);

    Long countAllNgonNguDaoTao();

    List<TdtNgonNguDaoTao> getAllNgonNguDaoTao();

    int create(TdtNgonNguDaoTao record);

    int deleteByTrainingId(BigDecimal trainingId);
}
