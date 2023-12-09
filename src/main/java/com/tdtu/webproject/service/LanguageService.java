package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtNgonNgu;
import com.tdtu.webproject.repository.LanguageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LanguageService {
    private final LanguageRepository languageRepository;

    public List<TdtNgonNgu> getAllLanguage() {
        return languageRepository.getAllLanguage();
    }
}
