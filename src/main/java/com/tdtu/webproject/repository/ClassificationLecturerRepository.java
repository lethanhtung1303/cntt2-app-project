package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtLecturerType;

import java.util.List;

public interface ClassificationLecturerRepository {
    Long countAllClassification();

    List<TdtLecturerType> getAllClassification();
}
