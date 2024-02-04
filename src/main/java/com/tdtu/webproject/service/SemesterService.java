package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtSchoolYear;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtSemester;
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
        List<TdtSchoolYear> schoolYearList = schoolYearRepository.findSchoolYear();
        List<TdtSemester> semesterList = semesterRepository.findSemester();

        Map<BigDecimal, List<Semester>> schoolYearSemesterMap = semesterList.stream()
                .collect(Collectors.groupingBy(TdtSemester::getSchoolYear,
                        Collectors.mapping(semester -> getSemester(semester.getSemester(), semesterList),
                                Collectors.toList())));

        return schoolYearList.stream()
                .map(trainingProcess -> SemesterDetailResponse.builder()
                        .value(trainingProcess.getSchoolYear())
                        .label(trainingProcess.getSchoolYearLabels())
                        .items(schoolYearSemesterMap.getOrDefault(trainingProcess.getSchoolYear(), Collections.emptyList()))
                        .build())
                .toList();
    }

    public Semester getSemester(BigDecimal semester, List<TdtSemester> semesterList) {
        return semesterList.stream()
                .filter(s -> s.getSemester().equals(semester))
                .map(this::buildSemester)
                .findFirst()
                .orElse(null);
    }

    public Semester buildSemester(TdtSemester semester) {
        return Semester.builder()
                .value(semester.getSemester())
                .label(semester.getSemesterLabels())
                .build();
    }
}
