package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtGraduationType;

import java.util.List;

public interface GraduationTypeRepository {
    Long countAllGraduationType();

    List<TdtGraduationType> getAllGraduationType();
}
