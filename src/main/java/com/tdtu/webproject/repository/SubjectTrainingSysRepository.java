package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtTrainingSystem;
import com.tdtu.webproject.mybatis.condition.SubjectTrainingSysCondition;

import java.math.BigDecimal;
import java.util.List;

public interface SubjectTrainingSysRepository {

    Long countSubjectTrainingSys(SubjectTrainingSysCondition condition);

    List<TdtTrainingSystem> findSubjectTrainingSys(SubjectTrainingSysCondition condition);

    TdtTrainingSystem getSubjectTrainingSysById(BigDecimal trainingSysId);
}
