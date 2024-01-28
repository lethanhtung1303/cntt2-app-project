package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtSchoolYear;

import java.util.List;

public interface SchoolYearRepository {
    Long countSchoolYear();

    List<TdtSchoolYear> findSchoolYear();
}
