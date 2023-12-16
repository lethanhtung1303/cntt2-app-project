package com.tdtu.webproject.service;

import com.tdtu.webproject.emun.LectureHoursStatus;
import com.tdtu.webproject.mybatis.result.NormsLectureHoursResult;
import com.tdtu.webproject.repository.LecturerRepository;
import com.tdtu.webproject.utils.ArrayUtil;
import generater.openapi.model.NormsLectureHoursDetailResponse;
import generater.openapi.model.NumberLessons;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.tdtu.webproject.constant.Const.*;


@Service
@AllArgsConstructor
public class NormsLectureHoursService {
    private final LecturerRepository lecturerRepository;

    public List<NormsLectureHoursDetailResponse> getNormsLectureHours(BigDecimal semester) {
        List<NormsLectureHoursResult> normsLectureHoursList = lecturerRepository.getNormsLectureHours();
        List<NormsLectureHoursDetailResponse> normsLectureHoursRawData = this.buildNormsLectureHoursDetailResponse(normsLectureHoursList);

        List<NormsLectureHoursResult> normsLectureHoursBySemester = normsLectureHoursList.stream()
                .filter(result -> result.getSemester() != null && result.getSemester().compareTo(semester) == 0)
                .collect(Collectors.toMap(NormsLectureHoursResult::getHistoryTeachingId,
                        Function.identity(),
                        BinaryOperator.minBy(Comparator.comparing(NormsLectureHoursResult::getDisplayOrder))))
                .values().stream()
                .toList();

        Map<BigDecimal, List<NormsLectureHoursResult>> normsLectureHoursBySemesterMap = normsLectureHoursBySemester.stream()
                .collect(Collectors.groupingBy(NormsLectureHoursResult::getLecturerId, Collectors.toList()));

        return Optional.ofNullable(normsLectureHoursRawData).isPresent()
                ? normsLectureHoursRawData.stream()
                .map(lecturer -> this.buildResponse(lecturer, normsLectureHoursBySemesterMap.getOrDefault(lecturer.getId(), Collections.emptyList())))
                .toList()
                .stream()
                .sorted(Comparator.comparing(NormsLectureHoursDetailResponse::getId))
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private NormsLectureHoursDetailResponse buildResponse(NormsLectureHoursDetailResponse lecturer, List<NormsLectureHoursResult> normsLectureHoursBySemester) {
        if (!ArrayUtil.isNotNullAndNotEmptyList(normsLectureHoursBySemester)) {
            return lecturer;
        }
        lecturer.setLessonsStandardSys(this.buildNumberLessons(normsLectureHoursBySemester, LESSONS_STANDARD_SYS));
        lecturer.setLessonsCLCVietnamese(this.buildNumberLessons(normsLectureHoursBySemester, LESSONS_CLC_VIETNAMESE));
        lecturer.setLessonsCLCEnglish(this.buildNumberLessons(normsLectureHoursBySemester, LESSONS_CLC_ENGLISH));
        lecturer.setLessonsEnglishInternational(this.buildNumberLessons(normsLectureHoursBySemester, LESSONS_ENGLISH_INTERNATIONAL));
        lecturer.setLessonsMaster(this.buildNumberLessons(normsLectureHoursBySemester, LESSONS_MASTER));
        lecturer.setConversionLesson(this.buildConversionLessons(lecturer.getLessonsStandardSys(),
                lecturer.getLessonsCLCVietnamese(),
                lecturer.getLessonsCLCEnglish(),
                lecturer.getLessonsEnglishInternational(),
                lecturer.getLessonsMaster()));
        lecturer.setTotalNumberLessons(this.countTotalNumberLessons(lecturer.getConversionLesson()));
        lecturer.setStatus(this.checkStatusNormsLectureHours(lecturer.getTotalNumberLessons(), lecturer.getClassificationLecturersCode(), lecturer.getDisplayOrder()));
        return lecturer;
    }

    private BigDecimal checkStatusNormsLectureHours(BigDecimal totalNumberLessons, BigDecimal lecturerTypeCode, BigDecimal displayOrder) {
        if (lecturerTypeCode.compareTo(BigDecimal.valueOf(FACULTY_LECTURER)) == 0) {
            if (displayOrder.compareTo(BigDecimal.valueOf(LEVEL_MASTER)) == 0) {
                if (totalNumberLessons.compareTo(BigDecimal.valueOf(NORMAL_NUMBER_LESSONS_MASTER)) < 0) {
                    return LectureHoursStatus.getValueByCode(LECTURER_HOURS_NORMAL);
                } else if (totalNumberLessons.compareTo(BigDecimal.valueOf(WARNING_NUMBER_LESSONS_MASTER)) < 0) {
                    return LectureHoursStatus.getValueByCode(LECTURER_HOURS_WARNING);
                }
            } else if (displayOrder.compareTo(BigDecimal.valueOf(LEVEL_DOCTORAL)) <= 0) {
                if (totalNumberLessons.compareTo(BigDecimal.valueOf(NORMAL_NUMBER_LESSONS_DOCTORAL)) < 0) {
                    return LectureHoursStatus.getValueByCode(LECTURER_HOURS_NORMAL);
                } else if (totalNumberLessons.compareTo(BigDecimal.valueOf(WARNING_NUMBER_LESSONS_DOCTORAL)) < 0) {
                    return LectureHoursStatus.getValueByCode(LECTURER_HOURS_WARNING);
                }
            }
        } else if (lecturerTypeCode.compareTo(BigDecimal.valueOf(VISITING_LECTURER)) == 0) {
            if (displayOrder.compareTo(BigDecimal.valueOf(LEVEL_MASTER)) == 0) {
                if (totalNumberLessons.compareTo(BigDecimal.valueOf(NORMAL_NUMBER_LESSONS_MASTER * 1.5)) < 0) {
                    return LectureHoursStatus.getValueByCode(LECTURER_HOURS_NORMAL);
                } else if (totalNumberLessons.compareTo(BigDecimal.valueOf(WARNING_NUMBER_LESSONS_MASTER * 1.5)) < 0) {
                    return LectureHoursStatus.getValueByCode(LECTURER_HOURS_WARNING);
                }
            } else if (displayOrder.compareTo(BigDecimal.valueOf(LEVEL_DOCTORAL)) <= 0) {
                if (totalNumberLessons.compareTo(BigDecimal.valueOf(NORMAL_NUMBER_LESSONS_DOCTORAL * 1.5)) < 0) {
                    return LectureHoursStatus.getValueByCode(LECTURER_HOURS_NORMAL);
                } else if (totalNumberLessons.compareTo(BigDecimal.valueOf(WARNING_NUMBER_LESSONS_DOCTORAL * 1.5)) < 0) {
                    return LectureHoursStatus.getValueByCode(LECTURER_HOURS_WARNING);
                }
            }
        }
        return LectureHoursStatus.getValueByCode(LECTURER_HOURS_LEVELOUT);
    }


    private BigDecimal countTotalNumberLessons(NumberLessons conversionLesson) {
        return conversionLesson.getNumberTheory().add(conversionLesson.getNumberPractice());
    }

    private NumberLessons buildConversionLessons(NumberLessons lessonsStandardSys,
                                                 NumberLessons lessonsCLCVietnamese,
                                                 NumberLessons lessonsCLCEnglish,
                                                 NumberLessons lessonsEnglishInternational,
                                                 NumberLessons lessonsMaster) {
        BigDecimal numberTheory = BigDecimal.ZERO;
        BigDecimal numberPractice = BigDecimal.ZERO;

        numberTheory = numberTheory.add(lessonsStandardSys.getNumberTheory().multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_STANDARD_SYS)))
                .add(lessonsCLCVietnamese.getNumberTheory().multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_CLC_VIETNAMESE)))
                .add(lessonsCLCEnglish.getNumberTheory().multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_CLC_ENGLISH)))
                .add(lessonsEnglishInternational.getNumberTheory().multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_ENGLISH_INTERNATIONAL)))
                .add(lessonsMaster.getNumberTheory().multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_MASTER)));

        numberPractice = numberPractice.add(lessonsStandardSys.getNumberPractice().multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_STANDARD_SYS)))
                .add(lessonsCLCVietnamese.getNumberPractice().multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_CLC_VIETNAMESE)))
                .add(lessonsCLCEnglish.getNumberPractice().multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_CLC_ENGLISH)))
                .add(lessonsEnglishInternational.getNumberPractice().multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_ENGLISH_INTERNATIONAL)))
                .add(lessonsMaster.getNumberPractice().multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_MASTER)));

        numberTheory = numberTheory.multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_THEORY));
        numberPractice = numberPractice.multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_PRACTICE));

        return NumberLessons.builder()
                .numberTheory(numberTheory)
                .numberPractice(numberPractice)
                .build();
    }

    private NumberLessons buildNumberLessons(List<NormsLectureHoursResult> normsLectureHoursBySemester, int trainingSystem) {
        BigDecimal totalTheory = normsLectureHoursBySemester.stream()
                .filter(result -> result.getTrainingSystem().compareTo(BigDecimal.valueOf(trainingSystem)) == 0
                        && result.getSubjectTypeCode().compareTo(BigDecimal.valueOf(THEORY_CODE)) == 0)
                .map(NormsLectureHoursResult::getNumberLessons)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPractice = normsLectureHoursBySemester.stream()
                .filter(result -> result.getTrainingSystem().compareTo(BigDecimal.valueOf(trainingSystem)) == 0
                        && result.getSubjectTypeCode().compareTo(BigDecimal.valueOf(PRACTICE_CODE)) == 0)
                .map(NormsLectureHoursResult::getNumberLessons)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return NumberLessons.builder()
                .numberTheory(totalTheory)
                .numberPractice(totalPractice)
                .build();
    }

    private List<NormsLectureHoursDetailResponse> buildNormsLectureHoursDetailResponse(List<NormsLectureHoursResult> normsLectureHoursList) {
        List<NormsLectureHoursResult> distinctNormsLectureHoursList = normsLectureHoursList.stream()
                .collect(Collectors.toMap(NormsLectureHoursResult::getLecturerId,
                        Function.identity(),
                        BinaryOperator.minBy(Comparator.comparing(NormsLectureHoursResult::getDisplayOrder))))
                .values().stream()
                .toList();
        return distinctNormsLectureHoursList.stream()
                .map(this::buildNormsLectureHoursDetail)
                .collect(Collectors.toList());
    }

    private NormsLectureHoursDetailResponse buildNormsLectureHoursDetail(NormsLectureHoursResult normsLectureHours) {
        return NormsLectureHoursDetailResponse.builder()
                .id(normsLectureHours.getLecturerId())
                .fullName(normsLectureHours.getFullName())
                .images(normsLectureHours.getImages())
                .emailTdtu(normsLectureHours.getEmailTdtu())
                .classificationLecturersCode(normsLectureHours.getLecturerTypeCode())
                .classificationLecturers(normsLectureHours.getLecturerType())
                .lecturerLevelCode(normsLectureHours.getLevelCode())
                .lecturerLevel(normsLectureHours.getLevel())
                .displayOrder(normsLectureHours.getDisplayOrder())
                .lessonsStandardSys(NumberLessons.builder()
                        .numberTheory(BigDecimal.ZERO)
                        .numberPractice(BigDecimal.ZERO)
                        .build())
                .lessonsCLCVietnamese(NumberLessons.builder()
                        .numberTheory(BigDecimal.ZERO)
                        .numberPractice(BigDecimal.ZERO)
                        .build())
                .lessonsCLCEnglish(NumberLessons.builder()
                        .numberTheory(BigDecimal.ZERO)
                        .numberPractice(BigDecimal.ZERO)
                        .build())
                .lessonsEnglishInternational(NumberLessons.builder()
                        .numberTheory(BigDecimal.ZERO)
                        .numberPractice(BigDecimal.ZERO)
                        .build())
                .lessonsMaster(NumberLessons.builder()
                        .numberTheory(BigDecimal.ZERO)
                        .numberPractice(BigDecimal.ZERO)
                        .build())
                .conversionLesson(NumberLessons.builder()
                        .numberTheory(BigDecimal.ZERO)
                        .numberPractice(BigDecimal.ZERO)
                        .build())
                .totalNumberLessons(BigDecimal.ZERO)
                .status(LectureHoursStatus.getValueByCode(LECTURER_HOURS_NOTTEACHING))
                .build();
    }
}
