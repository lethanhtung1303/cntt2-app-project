package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtDegree;

import java.util.List;

public interface LevelRepository {
    Long countAllLevel();

    List<TdtDegree> getAllLevel();
}
