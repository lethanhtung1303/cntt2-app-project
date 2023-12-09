package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtLoaiTotNghiep;

import java.util.List;

public interface GraduationTypeRepository {
    Long countAllGraduationType();

    List<TdtLoaiTotNghiep> getAllGraduationType();
}
