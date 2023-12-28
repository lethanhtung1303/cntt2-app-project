package com.tdtu.webproject.service;

import com.tdtu.webproject.mybatis.result.NormsLectureHoursResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.tdtu.webproject.constant.Const.*;

@Service
@AllArgsConstructor
public class NormsLectureHoursManageService {
    public List<NormsLectureHoursResult> conversionNormsLectureHours(List<NormsLectureHoursResult> normsLectureHoursBySemester) {
        return normsLectureHoursBySemester.stream()
                .peek(result -> {
                    BigDecimal numberLessons = result.getNumberLessons();
                    BigDecimal trainingSystem = result.getTrainingSystem();
                    BigDecimal subjectTypeCode = result.getSubjectTypeCode();

                    if (subjectTypeCode.compareTo(BigDecimal.valueOf(THEORY_CODE)) == 0) {
                        if (trainingSystem.compareTo(BigDecimal.valueOf(LESSONS_STANDARD_SYS)) == 0) {
                            result.setNumberLessons(numberLessons.multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_THEORY)).multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_STANDARD_SYS)));
                        } else if (trainingSystem.compareTo(BigDecimal.valueOf(LESSONS_CLC_VIETNAMESE)) == 0) {
                            result.setNumberLessons(numberLessons.multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_THEORY)).multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_CLC_VIETNAMESE)));
                        } else if (trainingSystem.compareTo(BigDecimal.valueOf(LESSONS_CLC_ENGLISH)) == 0) {
                            result.setNumberLessons(numberLessons.multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_THEORY)).multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_CLC_ENGLISH)));
                        } else if (trainingSystem.compareTo(BigDecimal.valueOf(LESSONS_ENGLISH_INTERNATIONAL)) == 0) {
                            result.setNumberLessons(numberLessons.multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_THEORY)).multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_ENGLISH_INTERNATIONAL)));
                        } else if (trainingSystem.compareTo(BigDecimal.valueOf(LESSONS_MASTER)) == 0) {
                            result.setNumberLessons(numberLessons.multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_THEORY)).multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_MASTER)));
                        }
                    } else if (subjectTypeCode.compareTo(BigDecimal.valueOf(PRACTICE_CODE)) == 0) {
                        if (trainingSystem.compareTo(BigDecimal.valueOf(LESSONS_STANDARD_SYS)) == 0) {
                            result.setNumberLessons(numberLessons.multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_PRACTICE)).multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_STANDARD_SYS)));
                        } else if (trainingSystem.compareTo(BigDecimal.valueOf(LESSONS_CLC_VIETNAMESE)) == 0) {
                            result.setNumberLessons(numberLessons.multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_PRACTICE)).multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_CLC_VIETNAMESE)));
                        } else if (trainingSystem.compareTo(BigDecimal.valueOf(LESSONS_CLC_ENGLISH)) == 0) {
                            result.setNumberLessons(numberLessons.multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_PRACTICE)).multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_CLC_ENGLISH)));
                        } else if (trainingSystem.compareTo(BigDecimal.valueOf(LESSONS_ENGLISH_INTERNATIONAL)) == 0) {
                            result.setNumberLessons(numberLessons.multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_PRACTICE)).multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_ENGLISH_INTERNATIONAL)));
                        } else if (trainingSystem.compareTo(BigDecimal.valueOf(LESSONS_MASTER)) == 0) {
                            result.setNumberLessons(numberLessons.multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_PRACTICE)).multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_MASTER)));
                        }
                    }
                })
                .collect(Collectors.toList());
    }
}
