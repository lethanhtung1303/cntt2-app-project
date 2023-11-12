package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtTrinhDo;

import java.util.List;

public interface LevelRepository {
    Long countAllLevel();

    List<TdtTrinhDo> getAllLevel();
}
