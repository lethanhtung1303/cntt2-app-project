package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtCertificate;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtLecturer;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtTrainingProcess;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtSatisfactoryScore;
import com.tdtu.webproject.mybatis.condition.LecturerCondition;
import com.tdtu.webproject.mybatis.condition.NormsLectureHoursCondition;
import com.tdtu.webproject.mybatis.result.NormsLectureHoursResult;
import com.tdtu.webproject.repository.LecturerRepository;
import com.tdtu.webproject.utils.ArrayUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LecturerManageService {

    private final LecturerRepository lecturerRepository;

    public List<TdtCertificate> getAllCertificateOfLecturer(BigDecimal lecturerId, List<TdtCertificate> certificateList) {
        return certificateList.stream()
                .filter(certificate -> certificate.getLecturerId().equals(lecturerId))
                .collect(Collectors.toList());
    }

    public List<TdtTrainingProcess> getAllTrainingProcessOfLecturer(BigDecimal lecturerId, List<TdtTrainingProcess> trainingProcessList) {
        return trainingProcessList.stream()
                .filter(trainingProcess -> trainingProcess.getLecturerId().equals(lecturerId))
                .collect(Collectors.toList());
    }

    public List<TdtSatisfactoryScore> getAllSatisfactionScoreListOfLecturer(BigDecimal lecturerId,
                                                                            List<TdtSatisfactoryScore> satisfactionScoreList) {
        return satisfactionScoreList.stream()
                .filter(satisfactionScore -> satisfactionScore.getLecturerId().equals(lecturerId))
                .collect(Collectors.toList());
    }

    public List<TdtLecturer> getAllLecturer() {
        return lecturerRepository.findLecturer(LecturerCondition.builder().build());
    }

    public List<NormsLectureHoursResult> getAllNormsLectureHoursResult(NormsLectureHoursCondition condition) {
        return lecturerRepository.getNormsLectureHours(condition);
    }

    public Map<BigDecimal, List<NormsLectureHoursResult>> getNormsLectureHoursBySemesterMap(List<NormsLectureHoursResult> normsLectureHoursList, BigDecimal semester) {
        List<NormsLectureHoursResult> normsLectureHoursBySemester = normsLectureHoursList.stream()
                .filter(result -> result.getSemester() != null && result.getSemester().compareTo(semester) == 0)
                .collect(Collectors.toMap(NormsLectureHoursResult::getHistoryTeachingId,
                        Function.identity(),
                        BinaryOperator.minBy(Comparator.comparing(NormsLectureHoursResult::getDisplayOrder))))
                .values().stream()
                .toList();

        return normsLectureHoursBySemester.stream()
                .collect(Collectors.groupingBy(NormsLectureHoursResult::getLecturerId, Collectors.toList()));
    }

    public boolean checkNotExistLecturer(BigDecimal lecturerId) {
        List<TdtLecturer> lecturerList = lecturerRepository
                .findLecturer(
                        LecturerCondition.builder()
                                .lecturerIds(List.of(lecturerId))
                                .build());
        return !ArrayUtil.isNotNullAndNotEmptyList(lecturerList);
    }
}
