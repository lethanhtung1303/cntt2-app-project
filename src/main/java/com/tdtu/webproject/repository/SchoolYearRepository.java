package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtNamHoc;

import java.util.List;

public interface SchoolYearRepository {
    Long countSchoolYear();

    List<TdtNamHoc> findSchoolYear();
}
