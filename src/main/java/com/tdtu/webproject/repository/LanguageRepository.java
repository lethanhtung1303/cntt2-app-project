package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtLanguage;

import java.util.List;

public interface LanguageRepository {
    Long countAllLanguage();

    List<TdtLanguage> getAllLanguage();
}
