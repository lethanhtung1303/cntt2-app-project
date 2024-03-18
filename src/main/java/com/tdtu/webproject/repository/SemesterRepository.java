package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtSemester;

import java.util.List;

public interface SemesterRepository {
    Long countSemester();

    List<TdtSemester> findSemester();
}
