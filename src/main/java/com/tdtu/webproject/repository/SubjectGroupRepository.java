package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtNhomMon;

import java.util.List;

public interface SubjectGroupRepository {
    Long countAllSubjectGroup();

    List<TdtNhomMon> getAllSubjectGroup();

    TdtNhomMon getSubjectGroupById(String groupId);
}
