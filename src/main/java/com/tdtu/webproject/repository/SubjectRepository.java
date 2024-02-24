package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtSubject;
import com.tdtu.webproject.mybatis.condition.SubjectCondition;

import java.util.List;

public interface SubjectRepository {
    Long countSubject(SubjectCondition condition);

    List<TdtSubject> findSubject(SubjectCondition condition);

    List<TdtSubject> getAllSubject();

    int create(TdtSubject record);

    int delete(SubjectCondition condition);

    int update(TdtSubject record, String subjectId);
}
