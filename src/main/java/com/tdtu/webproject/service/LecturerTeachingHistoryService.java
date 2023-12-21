package com.tdtu.webproject.service;

import com.tdtu.webproject.exception.BusinessException;
import com.tdtu.webproject.model.condition.LecturerTeachingHistoryCondition;
import com.tdtu.webproject.mybatis.result.LecturerTeachingHistoryResult;
import com.tdtu.webproject.repository.LecturerRepository;
import generater.openapi.model.TeachingHistoryDetailResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LecturerTeachingHistoryService {

    private final LecturerManageService lecturerManageService;
    private final LecturerRepository lecturerRepository;

    public List<TeachingHistoryDetailResponse> getTeachingHistory(BigDecimal lecturerId, BigDecimal semester) {
        if (Optional.ofNullable(lecturerId).isPresent()) {
            if (lecturerManageService.checkNotExistLecturer(lecturerId)) {
                throw new BusinessException("40001", "Not found Lecturer with ID: " + lecturerId);
            }
        }
        LecturerTeachingHistoryCondition condition = this.buildLecturerTeachingHistoryCondition(lecturerId, semester);
        List<LecturerTeachingHistoryResult> teachingHistoryList = lecturerRepository.getLecturerTeachingHistory(condition);

        return Optional.ofNullable(teachingHistoryList).isPresent()
                ? teachingHistoryList.stream()
                .map(this::buildResponse)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private LecturerTeachingHistoryCondition buildLecturerTeachingHistoryCondition(BigDecimal lecturerId, BigDecimal semester) {
        return LecturerTeachingHistoryCondition.builder()
                .lecturerId(lecturerId)
                .semester(semester)
                .build();
    }

    private TeachingHistoryDetailResponse buildResponse(LecturerTeachingHistoryResult result) {
        return TeachingHistoryDetailResponse.builder()
                .subjectCode(result.getSubjectCode())
                .subjectTitle(result.getSubjectTitle())
                .numberLessons(result.getNumberLessons())
                .subjectGroupCode(result.getSubjectGroupCode())
                .subjectGroupName(result.getSubjectGroupName())
                .subjectTypeCode(result.getSubjectTypeCode())
                .nameTypeSubject(result.getNameTypeSubject())
                .trainingSysCode(result.getTrainingSysCode())
                .nameTrainingSys(result.getNameTrainingSys())
                .identification(result.getIdentification())
                .build();
    }
}