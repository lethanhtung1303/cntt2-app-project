package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtLoaiMon;

import java.math.BigDecimal;
import java.util.List;

public interface SubjectTypeRepository {
    Long countAllSubjectType();

    List<TdtLoaiMon> getAllSubjectType();

    TdtLoaiMon getSubjectTypeById(BigDecimal typeId);
}
