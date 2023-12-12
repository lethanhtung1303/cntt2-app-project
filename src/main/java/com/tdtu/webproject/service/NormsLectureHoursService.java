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
        lecturer.setPracticalLessons(this.buildPracticalLessons(normsLectureHoursBySemester));
        lecturer.setConversionLesson(this.buildConversionLessons(lecturer.getPracticalLessons()));
        lecturer.setTotalNumberLessons(this.countTotalNumberLessons(lecturer.getConversionLesson()));
        lecturer.setStatus(this.checkStatusNormsLectureHours(lecturer.getTotalNumberLessons(), lecturer.getClassificationLecturersCode(), lecturer.getDisplayOrder()));
        return lecturer;
    }

    private BigDecimal checkStatusNormsLectureHours(BigDecimal totalNumberLessons, BigDecimal lecturerTypeCode, BigDecimal displayOrder) {
        if (lecturerTypeCode.compareTo(BigDecimal.ONE) == 0) {
            if (displayOrder.compareTo(BigDecimal.valueOf(4)) == 0) {
                if (totalNumberLessons.compareTo(BigDecimal.valueOf(207)) < 0) {
                    return LectureHoursStatus.getValueByCode(LECTURER_HOURS_NORMAL);
                } else if (totalNumberLessons.compareTo(BigDecimal.valueOf(350)) < 0) {
                    return LectureHoursStatus.getValueByCode(LECTURER_HOURS_WARNING);
                }
            } else if (displayOrder.compareTo(BigDecimal.valueOf(3)) <= 0) {
                if (totalNumberLessons.compareTo(BigDecimal.valueOf(243)) < 0) {
                    return LectureHoursStatus.getValueByCode(LECTURER_HOURS_NORMAL);
                } else if (totalNumberLessons.compareTo(BigDecimal.valueOf(350)) < 0) {
                    return LectureHoursStatus.getValueByCode(LECTURER_HOURS_WARNING);
                }
            }
        } else if (lecturerTypeCode.compareTo(BigDecimal.ZERO) == 0) {
            if (displayOrder.compareTo(BigDecimal.valueOf(4)) == 0) {
                if (totalNumberLessons.compareTo(BigDecimal.valueOf(310)) < 0) {
                    return LectureHoursStatus.getValueByCode(LECTURER_HOURS_NORMAL);
                } else if (totalNumberLessons.compareTo(BigDecimal.valueOf(525)) < 0) {
                    return LectureHoursStatus.getValueByCode(LECTURER_HOURS_WARNING);
                }
            } else if (displayOrder.compareTo(BigDecimal.valueOf(3)) <= 0) {
                if (totalNumberLessons.compareTo(BigDecimal.valueOf(364)) < 0) {
                    return LectureHoursStatus.getValueByCode(LECTURER_HOURS_NORMAL);
                } else if (totalNumberLessons.compareTo(BigDecimal.valueOf(525)) < 0) {
                    return LectureHoursStatus.getValueByCode(LECTURER_HOURS_WARNING);
                }
            }
        }
        return LectureHoursStatus.getValueByCode(LECTURER_HOURS_LEVELOUT);
    }


    private BigDecimal countTotalNumberLessons(NumberLessons conversionLesson) {
        return conversionLesson.getNumberTheory().add(conversionLesson.getNumberPractice());
    }

    private NumberLessons buildConversionLessons(NumberLessons practicalLessons) {
        return NumberLessons.builder()
                .numberTheory(practicalLessons.getNumberTheory().multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_THEORY)))
                .numberPractice(practicalLessons.getNumberPractice().multiply(BigDecimal.valueOf(CONVERSION_COEFFICIENT_PRACTICE)))
                .build();
    }

    private NumberLessons buildPracticalLessons(List<NormsLectureHoursResult> normsLectureHoursBySemester) {
        BigDecimal totalTheory = normsLectureHoursBySemester.stream()
                .filter(result -> result.getSubjectTypeCode().compareTo(BigDecimal.ZERO) == 0)
                .map(NormsLectureHoursResult::getNumberLessons)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPractice = normsLectureHoursBySemester.stream()
                .filter(result -> result.getSubjectTypeCode().compareTo(BigDecimal.ONE) == 0)
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
                .practicalLessons(NumberLessons.builder()
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
