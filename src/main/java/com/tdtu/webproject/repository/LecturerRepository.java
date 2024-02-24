package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtLecturer;
import com.tdtu.webproject.mybatis.condition.LecturerCondition;
import com.tdtu.webproject.mybatis.condition.LecturerTeachingHistoryCondition;
import com.tdtu.webproject.mybatis.condition.NormsLectureHoursCondition;
import com.tdtu.webproject.mybatis.result.*;

import java.math.BigDecimal;
import java.util.List;

public interface LecturerRepository {
    Long countLecturer(LecturerCondition condition);

    List<TdtLecturer> findLecturer(LecturerCondition condition);

    int update(TdtLecturer record, BigDecimal lecturerId);

    int delete(LecturerCondition condition);

    int create(TdtLecturer record);

    List<LecturerTeachingResult> getLecturerTeaching();

    List<LecturerTrainingProcessResult> getLecturerTrainingProcess();

    List<LecturerTrainingLanguageResult> getLecturerTrainingLanguage();

    List<LecturerCertificateResult> getLecturerCertificate();

    List<LecturerSatisfactionScoreResult> getLecturerSatisfactionScore(BigDecimal semester);

    List<NormsLectureHoursResult> getNormsLectureHours(NormsLectureHoursCondition condition);

    List<LecturerTeachingHistoryResult> getLecturerTeachingHistory(LecturerTeachingHistoryCondition condition);
}
