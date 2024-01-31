package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtSubjectGroup;
import com.tdtu.webproject.mybatis.condition.SubjectGroupCondition;

import java.util.List;

public interface SubjectGroupRepository {
    Long countAllSubjectGroup();

    Long countSubjectGroup(SubjectGroupCondition condition);

    List<TdtSubjectGroup> getAllSubjectGroup();

    TdtSubjectGroup getSubjectGroupById(String groupId);

    List<TdtSubjectGroup> findSubjectGroup(SubjectGroupCondition condition);
}
