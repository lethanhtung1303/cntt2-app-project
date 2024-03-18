package com.tdtu.webproject.service;

import com.tdtu.webproject.emun.LectureHoursStatus;
import com.tdtu.webproject.mybatis.condition.NormsLectureHoursCondition;
import com.tdtu.webproject.mybatis.result.NormsLectureHoursResult;
import com.tdtu.webproject.utils.ArrayUtil;
import generater.openapi.model.NormsLectureHoursDetailResponse;
import generater.openapi.model.NumberLessons;
import generater.openapi.model.NumberLessonsDetail;
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
    private final LecturerManageService lecturerManageService;
    private final NormsLectureHoursManageService normsLectureHoursManageService;

    public List<NormsLectureHoursDetailResponse> getNormsLectureHours(BigDecimal semester) {
        List<NormsLectureHoursResult> normsLectureHoursList = lecturerManageService.getAllNormsLectureHoursResult(NormsLectureHoursCondition.builder().build());
        List<NormsLectureHoursDetailResponse> normsLectureHoursRawData = this.buildNormsLectureHoursDetailResponse(normsLectureHoursList);

        Map<BigDecimal, List<NormsLectureHoursResult>> normsLectureHoursBySemesterMap = lecturerManageService.getNormsLectureHoursBySemesterMap(normsLectureHoursList, semester);

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

        lecturer.getLessonsStandardSys().setOriginalLesson((this.buildNumberLessons(normsLectureHoursBySemester, LESSONS_STANDARD_SYS)));
        lecturer.getLessonsCLCVietnamese().setOriginalLesson((this.buildNumberLessons(normsLectureHoursBySemester, LESSONS_CLC_VIETNAMESE)));
        lecturer.getLessonsCLCEnglish().setOriginalLesson((this.buildNumberLessons(normsLectureHoursBySemester, LESSONS_CLC_ENGLISH)));
        lecturer.getLessonsEnglishInternational().setOriginalLesson((this.buildNumberLessons(normsLectureHoursBySemester, LESSONS_ENGLISH_INTERNATIONAL)));
        lecturer.getLessonsMaster().setOriginalLesson((this.buildNumberLessons(normsLectureHoursBySemester, LESSONS_MASTER)));

        List<NormsLectureHoursResult> normsLectureHoursConversion = normsLectureHoursManageService.conversionNormsLectureHours(normsLectureHoursBySemester);

        lecturer.getLessonsStandardSys().setConversionLessons((this.buildNumberLessons(normsLectureHoursConversion, LESSONS_STANDARD_SYS)));
        lecturer.getLessonsCLCVietnamese().setConversionLessons((this.buildNumberLessons(normsLectureHoursConversion, LESSONS_CLC_VIETNAMESE)));
        lecturer.getLessonsCLCEnglish().setConversionLessons((this.buildNumberLessons(normsLectureHoursConversion, LESSONS_CLC_ENGLISH)));
        lecturer.getLessonsEnglishInternational().setConversionLessons((this.buildNumberLessons(normsLectureHoursConversion, LESSONS_ENGLISH_INTERNATIONAL)));
        lecturer.getLessonsMaster().setConversionLessons((this.buildNumberLessons(normsLectureHoursConversion, LESSONS_MASTER)));

        lecturer.setConversionLesson(this.buildConversionLessons(lecturer.getLessonsStandardSys().getConversionLessons(),
                lecturer.getLessonsCLCVietnamese().getConversionLessons(),
                lecturer.getLessonsCLCEnglish().getConversionLessons(),
                lecturer.getLessonsEnglishInternational().getConversionLessons(),
                lecturer.getLessonsMaster().getConversionLessons()));
        lecturer.setTotalNumberLessons(this.countTotalNumberLessons(lecturer.getConversionLesson()));
        lecturer.setStatus(this.checkStatusNormsLectureHours(lecturer.getTotalNumberLessons(), lecturer.getClassificationLecturersCode(), lecturer.getDisplayOrder()));
        return lecturer;
    }

    private BigDecimal checkStatusNormsLectureHours(BigDecimal totalNumberLessons, BigDecimal lecturerTypeCode, BigDecimal displayOrder) {
        if (lecturerTypeCode.compareTo(BigDecimal.valueOf(CONTRACTUAL_LECTURER)) == 0) {
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

        numberTheory = numberTheory.add(lessonsStandardSys.getNumberTheory())
                .add(lessonsCLCVietnamese.getNumberTheory())
                .add(lessonsCLCEnglish.getNumberTheory())
                .add(lessonsEnglishInternational.getNumberTheory())
                .add(lessonsMaster.getNumberTheory());

        numberPractice = numberPractice.add(lessonsStandardSys.getNumberPractice())
                .add(lessonsCLCVietnamese.getNumberPractice())
                .add(lessonsCLCEnglish.getNumberPractice())
                .add(lessonsEnglishInternational.getNumberPractice())
                .add(lessonsMaster.getNumberPractice());

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
                .lessonsStandardSys(NumberLessonsDetail.builder()
                        .originalLesson(NumberLessons.builder()
                                .numberTheory(BigDecimal.ZERO)
                                .numberPractice(BigDecimal.ZERO)
                                .build())
                        .conversionLessons(NumberLessons.builder()
                                .numberTheory(BigDecimal.ZERO)
                                .numberPractice(BigDecimal.ZERO)
                                .build())
                        .build())
                .lessonsCLCVietnamese(NumberLessonsDetail.builder()
                        .originalLesson(NumberLessons.builder()
                                .numberTheory(BigDecimal.ZERO)
                                .numberPractice(BigDecimal.ZERO)
                                .build())
                        .conversionLessons(NumberLessons.builder()
                                .numberTheory(BigDecimal.ZERO)
                                .numberPractice(BigDecimal.ZERO)
                                .build())
                        .build())
                .lessonsCLCEnglish(NumberLessonsDetail.builder()
                        .originalLesson(NumberLessons.builder()
                                .numberTheory(BigDecimal.ZERO)
                                .numberPractice(BigDecimal.ZERO)
                                .build())
                        .conversionLessons(NumberLessons.builder()
                                .numberTheory(BigDecimal.ZERO)
                                .numberPractice(BigDecimal.ZERO)
                                .build())
                        .build())
                .lessonsEnglishInternational(NumberLessonsDetail.builder()
                        .originalLesson(NumberLessons.builder()
                                .numberTheory(BigDecimal.ZERO)
                                .numberPractice(BigDecimal.ZERO)
                                .build())
                        .conversionLessons(NumberLessons.builder()
                                .numberTheory(BigDecimal.ZERO)
                                .numberPractice(BigDecimal.ZERO)
                                .build())
                        .build())
                .lessonsMaster(NumberLessonsDetail.builder()
                        .originalLesson(NumberLessons.builder()
                                .numberTheory(BigDecimal.ZERO)
                                .numberPractice(BigDecimal.ZERO)
                                .build())
                        .conversionLessons(NumberLessons.builder()
                                .numberTheory(BigDecimal.ZERO)
                                .numberPractice(BigDecimal.ZERO)
                                .build())
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
