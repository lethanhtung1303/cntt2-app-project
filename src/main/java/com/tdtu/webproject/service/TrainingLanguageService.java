package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtTrainingLanguage;
import com.tdtu.webproject.repository.TrainingLanguageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TrainingLanguageService {
    private final TrainingLanguageRepository trainingLanguageRepository;

    public List<TdtTrainingLanguage> getAllTrainingLanguage() {
        return trainingLanguageRepository.getAllTrainingLanguage();
    }
}
