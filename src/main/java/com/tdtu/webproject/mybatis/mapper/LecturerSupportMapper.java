package com.tdtu.webproject.mybatis.mapper;

import com.tdtu.webproject.model.condition.LecturerTeachingHistoryCondition;
import com.tdtu.webproject.mybatis.result.*;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface LecturerSupportMapper {
    List<LecturerTeachingResult> getLecturerTeaching();

    List<LecturerTrainingProcessResult> getLecturerTrainingProcess();

    List<LecturerTrainingLanguageResult> getLecturerTrainingLanguage();

    List<LecturerCertificateResult> getLecturerCertificate();

    List<LecturerSatisfactionScoreResult> getLecturerSatisfactionScore(BigDecimal semester);

    List<NormsLectureHoursResult> getNormsLectureHours();

    List<LecturerTeachingHistoryResult> getLecturerTeachingHistory(LecturerTeachingHistoryCondition condition);
}
