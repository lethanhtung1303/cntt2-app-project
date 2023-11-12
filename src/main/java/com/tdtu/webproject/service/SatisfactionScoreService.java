package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtDiemHaiLong;
import com.tdtu.webproject.model.condition.SatisfactionScoreCondition;
import com.tdtu.webproject.repository.SatisfactionScoreRepository;
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
public class SatisfactionScoreService {
    private final SatisfactionScoreRepository satisfactionScoreRepository;

    public List<TdtDiemHaiLong> findByLecturerId(String lecturerIds) {
        SatisfactionScoreCondition condition = this.buildSatisfactionScoreCondition(lecturerIds);
        return satisfactionScoreRepository.findSatisfactionScore(condition);
    }

    private SatisfactionScoreCondition buildSatisfactionScoreCondition(String lecturerIds) {
        return SatisfactionScoreCondition.builder()
                .lecturerIds(Optional.ofNullable(lecturerIds).isPresent()
                        ? Arrays.stream(lecturerIds.split(","))
                        .map(contact -> NumberUtil.toBigDeimal(contact).orElse(null))
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                // Add conditions if needed
                .build();
    }
}
