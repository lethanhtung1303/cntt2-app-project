package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtMonHoc;

import java.util.List;

public interface SubjectRepository {
    List<TdtMonHoc> getAllSubject();
}
