package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtNhatKyGiangDay;
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

        List<TdtNhatKyGiangDay> teachingHistoryList = teachingDiaryRepository.findTeachingDiary(condition);

        return Optional.ofNullable(teachingHistoryList).isPresent()
                ? teachingHistoryList.stream()
                .map(this::buildResponse)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private TeachingDiaryDetailResponse buildResponse(TdtNhatKyGiangDay result) {
        return TeachingDiaryDetailResponse.builder()
                .historyId(result.getLichSuGiangDayId())
                .absenceReport(result.getBaoVang())
                .compensationReport(result.getBaoBu())
                .reminderReport(result.getGiamThiNhacNho())
                .lateReport(result.getDiTre())
                .returnEarlyReport(result.getVeSom())
                .reportBehavior(result.getTacPhong())
                .incorrectGradingReport(result.getChamDiemSai())
                .lateSubmissionScoresReport(result.getNopBaiTre())
                .reviewReport(result.getPhucKhao())
                .manyPassingReports(result.getDauNhieu())
                .manyFailedReport(result.getRotNhieu())
                .build();
    }

    public String updateTeachingDiary(String historyId, TeachingDiaryUpdate teachingDiaryUpdate, String updateBy) {
        if (!ArrayUtil.isNotNullAndNotEmptyList(this.getTeachingDiary(historyId))) {
            throw new BusinessException("40002",
                    MessageProperties.getInstance().getProperty(TEACHING_DIARY_EMPTY)
            );
        }
        return teachingDiaryRepository.update(this.buildTdtNhatKyGiangDayForUpdate(teachingDiaryUpdate, updateBy), NumberUtil.toBigDecimal(historyId).get()) > 0
                ? SUCCESSFUL
                : FAIL;
    }

    private TdtNhatKyGiangDay buildTdtNhatKyGiangDayForUpdate(TeachingDiaryUpdate teachingDiaryUpdate, String updateBy) {
        return TdtNhatKyGiangDay.builder()
                .baoVang(teachingDiaryUpdate.getAbsenceReport())
                .baoBu(teachingDiaryUpdate.getCompensationReport())
                .giamThiNhacNho(teachingDiaryUpdate.getReminderReport())
                .diTre(teachingDiaryUpdate.getLateReport())
                .veSom(teachingDiaryUpdate.getReturnEarlyReport())
                .tacPhong(teachingDiaryUpdate.getReportBehavior())
                .chamDiemSai(teachingDiaryUpdate.getIncorrectGradingReport())
                .nopBaiTre(teachingDiaryUpdate.getLateSubmissionScoresReport())
                .phucKhao(teachingDiaryUpdate.getReviewReport())
                .dauNhieu(teachingDiaryUpdate.getManyPassingReports())
                .rotNhieu(teachingDiaryUpdate.getManyFailedReport())
                .updatedAt(DateUtil.getTimeNow())
                .updateBy(updateBy)
                .build();
    }
}
