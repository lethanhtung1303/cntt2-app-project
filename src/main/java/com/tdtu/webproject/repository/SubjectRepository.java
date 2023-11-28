package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtMonHoc;
import com.tdtu.webproject.model.condition.SubjectCondition;

import java.util.List;

public interface SubjectRepository {
    Long countSubject(SubjectCondition condition);

    List<TdtMonHoc> findSubject(SubjectCondition condition);

    List<TdtMonHoc> getAllSubject();
}
