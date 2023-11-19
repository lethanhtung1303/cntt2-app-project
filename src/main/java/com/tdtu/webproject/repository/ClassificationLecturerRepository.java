package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtLoaiGiangVien;

import java.util.List;

public interface ClassificationLecturerRepository {
    Long countAllClassification();

    List<TdtLoaiGiangVien> getAllClassification();
}
