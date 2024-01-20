package com.tdtu.webproject.service;

import com.tdtu.webproject.exception.BusinessException;
import com.tdtu.webproject.mybatis.condition.LecturerTeachingHistoryCondition;
import com.tdtu.webproject.mybatis.condition.TeachingHistoryCondition;
import com.tdtu.webproject.mybatis.result.LecturerTeachingHistoryResult;
import com.tdtu.webproject.repository.HistoryTeachingRepository;
import com.tdtu.webproject.repository.LecturerRepository;
import com.tdtu.webproject.utils.*;
import generater.openapi.model.TeachingHistoryDeleteRequest;
import generater.openapi.model.TeachingHistoryDetailResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tdtu.webproject.constant.Const.*;

@Service
@AllArgsConstructor
public class LecturerTeachingHistoryService {

    private final LecturerManageService lecturerManageService;
    private final LecturerRepository lecturerRepository;
    private final HistoryTeachingRepository historyTeachingRepository;

    public List<TeachingHistoryDetailResponse> getTeachingHistory(BigDecimal lecturerId, BigDecimal semester) {
        if (Optional.ofNullable(lecturerId).isPresent()) {
            if (lecturerManageService.checkNotExistLecturer(lecturerId)) {
                throw new BusinessException("40001",
                        MessageProperties.getInstance().getProperty(LECTURER_NOT_FOUND, StringUtil.convertBigDecimalToString(lecturerId))
                );
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
                .historyId(result.getHistoryId())
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
                .createDatetime(Optional.ofNullable(result.getCreateDatetime()).isPresent()
                        ? DateUtil.getValueFromLocalDateTime(result.getCreateDatetime(), DateUtil.DATETIME_FORMAT_HYPHEN)
                        : null)
                .build();
    }

    public String deleteTeachingHistory(TeachingHistoryDeleteRequest request) {
        TeachingHistoryCondition condition = this.buildTeachingHistoryConditionForDelete(request);
        if (!ArrayUtil.isNotNullAndNotEmptyList(condition.getHistoryId())) {
            throw new BusinessException("40002",
                    MessageProperties.getInstance().getProperty(DELETED_HISTORY_EMPTY)
            );
        }
        return historyTeachingRepository.delete(condition) > 0
                ? SUCCESSFUL
                : FAIL;
    }

    private TeachingHistoryCondition buildTeachingHistoryConditionForDelete(TeachingHistoryDeleteRequest request) {
        return TeachingHistoryCondition.builder()
                .historyId(Optional.ofNullable(request.getHistoryId()).isPresent()
                        ? Arrays.stream(request.getHistoryId().split(","))
                        .map(id -> NumberUtil.toBigDecimal(id).orElse(null))
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                .deleteBy(request.getDeleteBy())
                .build();
    }

    public List<TeachingHistoryDetailResponse> getTeachingHistoryDetail(String historyId) {
        BigDecimal id = NumberUtil.toBigDecimal(historyId).orElse(null);
        if (Optional.ofNullable(id).isEmpty()) {
            throw new BusinessException("40003",
                    MessageProperties.getInstance().getProperty(HISTORY_EMPTY)
            );
        }
        LecturerTeachingHistoryCondition condition = LecturerTeachingHistoryCondition.builder()
                .historyId(id)
                .build();

        List<LecturerTeachingHistoryResult> teachingHistoryList = lecturerRepository.getLecturerTeachingHistory(condition);

        return Optional.ofNullable(teachingHistoryList).isPresent()
                ? teachingHistoryList.stream()
                .map(this::buildResponse)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }
}
