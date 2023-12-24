package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtNhomMon;
import com.tdtu.webproject.mybatis.condition.SubjectGroupCondition;

import java.util.List;

public interface SubjectGroupRepository {
    Long countAllSubjectGroup();

    Long countSubjectGroup(SubjectGroupCondition condition);

    List<TdtNhomMon> getAllSubjectGroup();

    TdtNhomMon getSubjectGroupById(String groupId);

    List<TdtNhomMon> findSubjectGroup(SubjectGroupCondition condition);
}
