package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtGiangVien;
import com.tdtu.webproject.model.condition.LecturerCondition;

import java.util.List;

public interface LecturerRepository {
    Long countLecturer(LecturerCondition condition);

    List<TdtGiangVien> findLecturer(LecturerCondition condition);
}
