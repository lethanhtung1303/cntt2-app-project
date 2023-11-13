package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtNgonNguDaoTao;
import com.tdtu.webproject.model.condition.TrainingLanguageCondition;
import com.tdtu.webproject.repository.TrainingLanguageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TrainingProcessManageService {
    private final TrainingLanguageRepository trainingLanguageRepository;

    public List<TdtNgonNguDaoTao> getAllLanguageOfTrainingProcess(BigDecimal trainingProcessId) {
        return trainingLanguageRepository.findTrainingLanguage(this.buildTrainingLanguageCondition(trainingProcessId));
    }

    private TrainingLanguageCondition buildTrainingLanguageCondition(BigDecimal trainingProcessId) {
        return TrainingLanguageCondition.builder()
                .trainingProcessIds(Optional.ofNullable(trainingProcessId).isPresent()
                        ? List.of(trainingProcessId)
                        : Collections.emptyList())
                // Add conditions if needed
                .build();
    }
}
