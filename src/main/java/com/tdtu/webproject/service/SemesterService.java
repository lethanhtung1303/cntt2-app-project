package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtHocKy;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtNamHoc;
import com.tdtu.webproject.repository.SchoolYearRepository;
import com.tdtu.webproject.repository.SemesterRepository;
import generater.openapi.model.Semester;
import generater.openapi.model.SemesterDetailResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SemesterService {
    private final SchoolYearRepository schoolYearRepository;
    private final SemesterRepository semesterRepository;

    public List<SemesterDetailResponse> findAll() {
        List<TdtNamHoc> schoolYearList = schoolYearRepository.findSchoolYear();
        List<TdtHocKy> semesterList = semesterRepository.findSemester();

        Map<BigDecimal, List<Semester>> schoolYearSemesterMap = semesterList.stream()
                .collect(Collectors.groupingBy(TdtHocKy::getNamHoc,
                        Collectors.mapping(semester -> getSemester(semester.getHocKy(), semesterList),
                                Collectors.toList())));

        return schoolYearList.stream()
                .map(trainingProcess -> SemesterDetailResponse.builder()
                        .value(trainingProcess.getNamHoc())
                        .label(trainingProcess.getNamHocLabels())
                        .items(schoolYearSemesterMap.getOrDefault(trainingProcess.getNamHoc(), Collections.emptyList()))
                        .build())
                .toList();
    }

    public Semester getSemester(BigDecimal semester, List<TdtHocKy> semesterList) {
        return semesterList.stream()
                .filter(s -> s.getHocKy().equals(semester))
                .map(this::buildSemester)
                .findFirst()
                .orElse(null);
    }

    public Semester buildSemester(TdtHocKy semester) {
        return Semester.builder()
                .value(semester.getHocKy())
                .label(semester.getHocKyLabels())
                .build();
    }
}
