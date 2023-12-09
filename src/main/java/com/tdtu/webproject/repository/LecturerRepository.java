package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtGiangVien;
import com.tdtu.webproject.model.condition.LecturerCondition;

import java.math.BigDecimal;
import java.util.List;

public interface LecturerRepository {
    Long countLecturer(LecturerCondition condition);

    List<TdtGiangVien> findLecturer(LecturerCondition condition);

    int update(TdtGiangVien record, BigDecimal lecturerId);

    int delete(LecturerCondition condition);

    int create(TdtGiangVien record);
}
