package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtHocKy;

import java.util.List;

public interface SemesterRepository {
    Long countSemester();

    List<TdtHocKy> findSemester();
}
