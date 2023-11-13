package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtQuaTrinhDaoTao;
import com.tdtu.webproject.model.condition.TrainingProcessCondition;
import com.tdtu.webproject.repository.TrainingProcessRepository;
import com.tdtu.webproject.utils.NumberUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TrainingProcessService {
    private final TrainingProcessRepository trainingProcessRepository;

    public List<TdtQuaTrinhDaoTao> findByLecturerId(String lecturerIds) {
        TrainingProcessCondition condition = this.buildTrainingProcessCondition(lecturerIds);
        return trainingProcessRepository.findTrainingProcess(condition);
    }

    private TrainingProcessCondition buildTrainingProcessCondition(String lecturerIds) {
        return TrainingProcessCondition.builder()
                .lecturerIds(Optional.ofNullable(lecturerIds).isPresent()
                        ? Arrays.stream(lecturerIds.split(","))
                        .map(lecturerId -> NumberUtil.toBigDeimal(lecturerId).orElse(null))
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                // Add conditions if needed
                .build();
    }
}
