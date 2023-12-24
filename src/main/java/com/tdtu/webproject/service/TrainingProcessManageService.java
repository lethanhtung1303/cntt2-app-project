package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtNgonNguDaoTao;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtQuaTrinhDaoTao;
import com.tdtu.webproject.mybatis.condition.TrainingLanguageCondition;
import com.tdtu.webproject.mybatis.condition.TrainingProcessCondition;
import com.tdtu.webproject.repository.TrainingLanguageRepository;
import com.tdtu.webproject.repository.TrainingProcessRepository;
import com.tdtu.webproject.utils.ArrayUtil;
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
    private final TrainingProcessRepository trainingProcessRepository;

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

    public boolean checkExistTrainingProcess(BigDecimal processId) {
        List<TdtQuaTrinhDaoTao> trainingProcessList = trainingProcessRepository
                .findTrainingProcess(
                        TrainingProcessCondition.builder()
                                .processIds(List.of(processId))
                                .build());
        return ArrayUtil.isNotNullAndNotEmptyList(trainingProcessList);
    }
}
