package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtSubjectType;

import java.math.BigDecimal;
import java.util.List;

public interface SubjectTypeRepository {
    Long countAllSubjectType();

    List<TdtSubjectType> getAllSubjectType();

    TdtSubjectType getSubjectTypeById(BigDecimal typeId);
}
