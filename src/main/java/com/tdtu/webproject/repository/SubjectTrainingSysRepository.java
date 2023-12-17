package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtHeDaoTao;
import com.tdtu.webproject.model.condition.SubjectTrainingSysCondition;

import java.math.BigDecimal;
import java.util.List;

public interface SubjectTrainingSysRepository {

    Long countSubjectTrainingSys(SubjectTrainingSysCondition condition);

    List<TdtHeDaoTao> findSubjectTrainingSys(SubjectTrainingSysCondition condition);

    TdtHeDaoTao getSubjectTrainingSysById(BigDecimal trainingSysId);
}
