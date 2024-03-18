package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtSchoolYear;
import com.tdtu.webproject.repository.SchoolYearRepository;
import generater.openapi.model.SchoolYearDetailResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SchoolYearService {
    private final SchoolYearRepository schoolYearRepository;

    public List<SchoolYearDetailResponse> findAll() {
        List<TdtSchoolYear> schoolYearList = schoolYearRepository.findSchoolYear();

        return Optional.ofNullable(schoolYearList).isPresent()
                ? schoolYearList.stream()
                .map(this::buildSchoolYearDetailResponse)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    public SchoolYearDetailResponse buildSchoolYearDetailResponse(TdtSchoolYear schoolYear) {
        return SchoolYearDetailResponse.builder()
                .value(schoolYear.getSchoolYear())
                .label(schoolYear.getSchoolYearLabels())
                .build();
    }
}
