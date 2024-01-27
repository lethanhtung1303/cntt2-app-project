package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtTeachingLog;
import com.tdtu.webproject.exception.BusinessException;
import com.tdtu.webproject.mybatis.condition.TeachingDiaryCondition;
import com.tdtu.webproject.repository.TeachingDiaryRepository;
import com.tdtu.webproject.utils.ArrayUtil;
import com.tdtu.webproject.utils.DateUtil;
import com.tdtu.webproject.utils.MessageProperties;
import com.tdtu.webproject.utils.NumberUtil;
import generater.openapi.model.TeachingDiaryDetailResponse;
import generater.openapi.model.TeachingDiaryUpdate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tdtu.webproject.constant.Const.*;

@Service
@AllArgsConstructor
public class TeachingDiaryService {

    private final TeachingDiaryRepository teachingDiaryRepository;

    public List<TeachingDiaryDetailResponse> getTeachingDiary(String historyId) {
        BigDecimal id = NumberUtil.toBigDecimal(historyId).orElse(null);
        if (Optional.ofNullable(id).isEmpty()) {
            throw new BusinessException("40001",
                    MessageProperties.getInstance().getProperty(TEACHING_DIARY_EMPTY)
            );
        }
        TeachingDiaryCondition condition = TeachingDiaryCondition.builder()
                .historyId(id)
                .build();

        List<TdtTeachingLog> teachingHistoryList = teachingDiaryRepository.findTeachingDiary(condition);

        return Optional.ofNullable(teachingHistoryList).isPresent()
                ? teachingHistoryList.stream()
                .map(this::buildResponse)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private TeachingDiaryDetailResponse buildResponse(TdtTeachingLog result) {
        return TeachingDiaryDetailResponse.builder()
                .historyId(result.getTeachingHistoryId())
                .absenceReport(result.getAbsenceReport())
                .compensationReport(result.getCompensatoryLearningReport())
                .reminderReport(result.getSupervisorReminder())
                .lateReport(result.getLateArrival())
                .returnEarlyReport(result.getEarlyDeparture())
                .reportBehavior(result.getConduct())
                .incorrectGradingReport(result.getGradingError())
                .lateSubmissionScoresReport(result.getLateSubmission())
                .reviewReport(result.getReassessment())
                .manyPassingReports(result.getPassManyExams())
                .manyFailedReport(result.getFailManyExams())
                .build();
    }

    public String updateTeachingDiary(String historyId, TeachingDiaryUpdate teachingDiaryUpdate, String updateBy) {
        if (!ArrayUtil.isNotNullAndNotEmptyList(this.getTeachingDiary(historyId))) {
            throw new BusinessException("40002",
                    MessageProperties.getInstance().getProperty(TEACHING_DIARY_EMPTY)
            );
        }
        return teachingDiaryRepository.update(this.buildTdtTeachingLogForUpdate(teachingDiaryUpdate, updateBy), NumberUtil.toBigDecimal(historyId).get()) > 0
                ? SUCCESSFUL
                : FAIL;
    }

    private TdtTeachingLog buildTdtTeachingLogForUpdate(TeachingDiaryUpdate teachingDiaryUpdate, String updateBy) {
        return TdtTeachingLog.builder()
                .absenceReport(teachingDiaryUpdate.getAbsenceReport())
                .compensatoryLearningReport(teachingDiaryUpdate.getCompensationReport())
                .supervisorReminder(teachingDiaryUpdate.getReminderReport())
                .lateArrival(teachingDiaryUpdate.getLateReport())
                .earlyDeparture(teachingDiaryUpdate.getReturnEarlyReport())
                .conduct(teachingDiaryUpdate.getReportBehavior())
                .gradingError(teachingDiaryUpdate.getIncorrectGradingReport())
                .lateSubmission(teachingDiaryUpdate.getLateSubmissionScoresReport())
                .reassessment(teachingDiaryUpdate.getReviewReport())
                .passManyExams(teachingDiaryUpdate.getManyPassingReports())
                .failManyExams(teachingDiaryUpdate.getManyFailedReport())
                .updatedAt(DateUtil.getTimeNow())
                .updateBy(updateBy)
                .build();
    }
}
