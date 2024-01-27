package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtSatisfactoryScore;
import com.tdtu.webproject.exception.BusinessException;
import com.tdtu.webproject.mybatis.condition.SatisfactionScoreCondition;
import com.tdtu.webproject.repository.SatisfactionScoreRepository;
import com.tdtu.webproject.utils.ArrayUtil;
import com.tdtu.webproject.utils.NumberUtil;
import generater.openapi.model.SatisfactionScoreCreate;
import generater.openapi.model.SatisfactionScoreDeleteRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tdtu.webproject.constant.Const.FAIL;
import static com.tdtu.webproject.constant.Const.SUCCESSFUL;

@Service
@AllArgsConstructor
public class SatisfactionScoreService {
    private final LecturerManageService lecturerManageService;
    private final SatisfactionScoreRepository satisfactionScoreRepository;

    public List<TdtSatisfactoryScore> findByLecturerId(String lecturerIds) {
        SatisfactionScoreCondition condition = this.buildSatisfactionScoreConditionForFind(lecturerIds);
        return satisfactionScoreRepository.findSatisfactionScore(condition);
    }

    private SatisfactionScoreCondition buildSatisfactionScoreConditionForFind(String lecturerIds) {
        return SatisfactionScoreCondition.builder()
                .lecturerIds(Optional.ofNullable(lecturerIds).isPresent()
                        ? Arrays.stream(lecturerIds.split(","))
                        .map(lecturerId -> NumberUtil.toBigDecimal(lecturerId).orElse(null))
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                .build();
    }

    public String deleteSatisfactionScore(SatisfactionScoreDeleteRequest request) {
        SatisfactionScoreCondition condition = this.buildSatisfactionScoreConditionForDelete(request);
        if (!ArrayUtil.isNotNullAndNotEmptyList(condition.getSatisfactionScoreIds())) {
            throw new BusinessException("40001", "The list of deleted Lecturers is empty!");
        }
        return satisfactionScoreRepository.delete(condition) > 0
                ? SUCCESSFUL
                : FAIL;
    }

    private SatisfactionScoreCondition buildSatisfactionScoreConditionForDelete(SatisfactionScoreDeleteRequest request) {
        return SatisfactionScoreCondition.builder()
                .satisfactionScoreIds(Optional.ofNullable(request.getSatisfactionScoreId()).isPresent()
                        ? Arrays.stream(request.getSatisfactionScoreId().split(","))
                        .map(id -> NumberUtil.toBigDecimal(id).orElse(null))
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                .updateBy(request.getDeleteBy())
                .build();
    }

    public String createSatisfactionScore(BigDecimal lecturerId, SatisfactionScoreCreate satisfactionScoreCreate, String createBy) {
        if (Optional.ofNullable(lecturerId).isPresent()) {
            if (lecturerManageService.checkNotExistLecturer(lecturerId)) {
                throw new BusinessException("40001", "Not found Lecturer with ID: " + lecturerId);
            }

            return satisfactionScoreRepository.create(this.buildTdtDiemHaiLongForCreate(lecturerId, satisfactionScoreCreate), createBy) > 0
                    ? SUCCESSFUL
                    : FAIL;
        }
        throw new BusinessException("40005", "Lecturer Id is null!");
    }

    private TdtSatisfactoryScore buildTdtDiemHaiLongForCreate(BigDecimal lecturerId, SatisfactionScoreCreate satisfactionScoreCreate) {
        return TdtSatisfactoryScore.builder()
                .lecturerId(lecturerId)
                .subjectId(satisfactionScoreCreate.getMaMon())
                .semester(satisfactionScoreCreate.getHocKy())
                .satisfactoryScore(satisfactionScoreCreate.getDiemHaiLong())
                .build();
    }
}
