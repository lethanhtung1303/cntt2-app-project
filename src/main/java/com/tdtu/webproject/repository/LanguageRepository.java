package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtNgonNgu;

import java.util.List;

public interface LanguageRepository {
    Long countAllLanguage();

    List<TdtNgonNgu> getAllLanguage();
}
