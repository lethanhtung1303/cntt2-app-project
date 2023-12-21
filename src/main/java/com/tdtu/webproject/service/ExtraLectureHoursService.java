package com.tdtu.webproject.service;

import com.tdtu.webproject.model.condition.NormsLectureHoursCondition;
import com.tdtu.webproject.mybatis.result.NormsLectureHoursResult;
import com.tdtu.webproject.utils.ArrayUtil;
import generater.openapi.model.ExtraHours;
import generater.openapi.model.ExtraLectureHoursDetailResponse;
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
public class ExtraLectureHoursService {
    private final LecturerManageService lecturerManageService;

    public List<ExtraLectureHoursDetailResponse> getExtraHoursContractual(BigDecimal semester) {
        NormsLectureHoursCondition condition = NormsLectureHoursCondition.builder()
                .isContractual(BigDecimal.valueOf(CONTRACTUAL_LECTURER))
                .build();
        List<NormsLectureHoursResult> extraLectureHoursList = lecturerManageService.getAllNormsLectureHoursResult(condition);
        List<ExtraLectureHoursDetailResponse> extraLectureHoursListRawData = this.buildExtraLectureHoursDetailResponse(extraLectureHoursList);

        Map<BigDecimal, List<NormsLectureHoursResult>> extraLectureHoursBySemesterMap = lecturerManageService.getNormsLectureHoursBySemesterMap(extraLectureHoursList, semester);

        return Optional.ofNullable(extraLectureHoursListRawData).isPresent()
                ? extraLectureHoursListRawData.stream()
                .map(lecturer -> this.buildResponse(lecturer, extraLectureHoursBySemesterMap.getOrDefault(lecturer.getId(), Collections.emptyList())))
                .toList()
                .stream()
                .sorted(Comparator.comparing(ExtraLectureHoursDetailResponse::getId))
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private ExtraLectureHoursDetailResponse buildResponse(ExtraLectureHoursDetailResponse lecturer, List<NormsLectureHoursResult> normsLectureHoursBySemester) {
        lecturer.setStandardLectureHours(this.setStandardLectureHours(lecturer.getDisplayOrder()));

        if (!ArrayUtil.isNotNullAndNotEmptyList(normsLectureHoursBySemester)) {
            return lecturer;
        }
        List<NormsLectureHoursResult> normsLectureHoursConversion = this.conversionNormsLectureHours(normsLectureHoursBySemester);

        lecturer.setTotalLectureHours(this.setTotalLectureHours(normsLectureHoursConversion));

        List<NormsLectureHoursResult> extraHours = this.calculateExtraHours(lecturer.getStandardLectureHours(), normsLectureHoursConversion);

        lecturer.setExtraHoursStandardSys(this.setNumberLessons(extraHours, LESSONS_STANDARD_SYS));
        lecturer.setExtraHoursCLCVietnamese(this.setExtraHours(extraHours, LESSONS_CLC_VIETNAMESE));
        lecturer.setExtraHoursCLCEnglish(this.setExtraHours(extraHours, LESSONS_CLC_ENGLISH));
        lecturer.setExtraHoursEnglishInternational(this.setExtraHours(extraHours, LESSONS_ENGLISH_INTERNATIONAL));
        lecturer.setExtraHoursMaster(this.setNumberLessons(extraHours, LESSONS_MASTER));
        lecturer.setTotalPayment(this.setTotalPayment(lecturer.getExtraHoursStandardSys(),
                lecturer.getExtraHoursCLCVietnamese(),
                lecturer.getExtraHoursCLCEnglish(),
                lecturer.getExtraHoursEnglishInternational(),
                lecturer.getExtraHoursMaster()));
        return lecturer;
    }

    private BigDecimal setTotalPayment(BigDecimal extraHoursStandardSys,
                                       ExtraHours extraHoursCLCVietnamese,
                                       ExtraHours extraHoursCLCEnglish,
                                       ExtraHours extraHoursEnglishInternational,
                                       BigDecimal extraHoursMaster) {


        // Tính tổng giá trị
        BigDecimal totalPayment = BigDecimal.ZERO;

        // extraHoursStandardSys * 88000
        totalPayment = totalPayment.add(extraHoursStandardSys.multiply(BigDecimal.valueOf(STANDARD_SYS_RATE)));

        // extraHoursCLCVietnamese
        totalPayment = totalPayment.add(extraHoursCLCVietnamese.getBasicSubjects().multiply(BigDecimal.valueOf(CLC_VIETNAMESE_BASIC_RATE)));
        totalPayment = totalPayment.add(extraHoursCLCVietnamese.getMajoringSubjects().multiply(BigDecimal.valueOf(CLC_VIETNAMESE_MAJORING_RATE)));

        // extraHoursCLCEnglish
        totalPayment = totalPayment.add(extraHoursCLCEnglish.getBasicSubjects().multiply(BigDecimal.valueOf(CLC_ENGLISH_BASIC_RATE)));
        totalPayment = totalPayment.add(extraHoursCLCEnglish.getMajoringSubjects().multiply(BigDecimal.valueOf(CLC_ENGLISH_MAJORING_RATE)));

        // extraHoursEnglishInternational
        totalPayment = totalPayment.add(extraHoursEnglishInternational.getBasicSubjects().multiply(BigDecimal.valueOf(ENGLISH_INTL_BASIC_RATE)));
        totalPayment = totalPayment.add(extraHoursEnglishInternational.getMajoringSubjects().multiply(BigDecimal.valueOf(ENGLISH_INTL_MAJORING_RATE)));

        // extraHoursMaster * 232000
        totalPayment = totalPayment.add(extraHoursMaster.multiply(BigDecimal.valueOf(MASTER_RATE)));

        return totalPayment;
    }

    private ExtraHours setExtraHours(List<NormsLectureHoursResult> extraHours, int trainingSystem) {
        return ExtraHours.builder()
                .basicSubjects(extraHours.stream()
                        .filter(e -> e.getTrainingSystem().compareTo(BigDecimal.valueOf(trainingSystem)) == 0
                                && e.getSubjectGroupCode().equals(BASIC_SUBJECTS))
                        .map(NormsLectureHoursResult::getNumberLessons)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .majoringSubjects(extraHours.stream()
                        .filter(e -> e.getTrainingSystem().compareTo(BigDecimal.valueOf(trainingSystem)) == 0
                                && !e.getSubjectGroupCode().equals(BASIC_SUBJECTS))
                        .map(NormsLectureHoursResult::getNumberLessons)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .build();
    }

    private BigDecimal setNumberLessons(List<NormsLectureHoursResult> extraHours, int trainingSystem) {
        return extraHours.stream()
                .filter(result -> result.getTrainingSystem().compareTo(BigDecimal.valueOf(trainingSystem)) == 0)
                .map(NormsLectureHoursResult::getNumberLessons)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<NormsLectureHoursResult> calculateExtraHours(BigDecimal standardLectureHours, List<NormsLectureHoursResult> normsLectureHoursConversion) {
        // Sắp xếp normsLectureHoursConversion theo createDatetime tăng dần
        normsLectureHoursConversion.sort(Comparator.comparing(NormsLectureHoursResult::getCreateDatetime));

        // Tính tổng số tiết đã đăng ký
        BigDecimal totalLessons = BigDecimal.ZERO;
        List<NormsLectureHoursResult> extraHoursList = new ArrayList<>();

        for (NormsLectureHoursResult normsLectureHoursResult : normsLectureHoursConversion) {
            // Cộng dồn số tiết đã đăng ký
            totalLessons = totalLessons.add(normsLectureHoursResult.getNumberLessons());

            // Nếu tổng số tiết lớn hơn hoặc bằng standardLectureHours
            if (totalLessons.compareTo(standardLectureHours) >= 0) {
                // Nếu phần tử hiện tại là phần tử vượt quá standardLectureHours
                if (totalLessons.compareTo(standardLectureHours) > 0) {
                    // Cập nhật numberLessons của phần tử hiện tại bằng số tiết dư thừa
                    BigDecimal remainingLessons = totalLessons.subtract(standardLectureHours);
                    normsLectureHoursResult.setNumberLessons(remainingLessons);
                }

                // Thêm các phần tử còn lại vào danh sách trả về
                extraHoursList.addAll(normsLectureHoursConversion.subList(normsLectureHoursConversion.indexOf(normsLectureHoursResult), normsLectureHoursConversion.size()));
                break; // Dừng lại vì đã tìm đủ số tiết dư thừa
            }
        }

        return extraHoursList;
    }

    private List<NormsLectureHoursResult> conversionNormsLectureHours(List<NormsLectureHoursResult> normsLectureHoursBySemester) {
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

    private BigDecimal setTotalLectureHours(List<NormsLectureHoursResult> normsLectureHours) {
        return normsLectureHours.stream()
                .map(NormsLectureHoursResult::getNumberLessons)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal setStandardLectureHours(BigDecimal displayOrder) {
        if (displayOrder.compareTo(BigDecimal.valueOf(LEVEL_MASTER)) == 0) {
            return BigDecimal.valueOf(NORMAL_NUMBER_LESSONS_MASTER);
        } else if (displayOrder.compareTo(BigDecimal.valueOf(LEVEL_DOCTORAL)) <= 0) {
            return BigDecimal.valueOf(NORMAL_NUMBER_LESSONS_DOCTORAL);
        } else {
            return BigDecimal.ZERO;
        }
    }

    private List<ExtraLectureHoursDetailResponse> buildExtraLectureHoursDetailResponse(List<NormsLectureHoursResult> extraLectureHoursList) {
        List<NormsLectureHoursResult> distinctExtraLectureHoursList = extraLectureHoursList.stream()
                .filter(extraLectureHours -> extraLectureHours.getLecturerTypeCode().compareTo(BigDecimal.valueOf(CONTRACTUAL_LECTURER)) == 0)
                .collect(Collectors.toMap(NormsLectureHoursResult::getLecturerId,
                        Function.identity(),
                        BinaryOperator.minBy(Comparator.comparing(NormsLectureHoursResult::getDisplayOrder))))
                .values().stream()
                .toList();
        return distinctExtraLectureHoursList.stream()
                .map(this::buildExtraLectureHoursDetail)
                .collect(Collectors.toList());
    }

    private ExtraLectureHoursDetailResponse buildExtraLectureHoursDetail(NormsLectureHoursResult normsLectureHours) {
        return ExtraLectureHoursDetailResponse.builder()
                .id(normsLectureHours.getLecturerId())
                .fullName(normsLectureHours.getFullName())
                .images(normsLectureHours.getImages())
                .emailTdtu(normsLectureHours.getEmailTdtu())
                .classificationLecturersCode(normsLectureHours.getLecturerTypeCode())
                .classificationLecturers(normsLectureHours.getLecturerType())
                .lecturerLevelCode(normsLectureHours.getLevelCode())
                .lecturerLevel(normsLectureHours.getLevel())
                .displayOrder(normsLectureHours.getDisplayOrder())
                .standardLectureHours(BigDecimal.ZERO)
                .totalLectureHours(BigDecimal.ZERO)
                .extraHoursStandardSys(BigDecimal.ZERO)
                .extraHoursCLCVietnamese(ExtraHours.builder()
                        .basicSubjects(BigDecimal.ZERO)
                        .majoringSubjects(BigDecimal.ZERO)
                        .build())
                .extraHoursCLCEnglish(ExtraHours.builder()
                        .basicSubjects(BigDecimal.ZERO)
                        .majoringSubjects(BigDecimal.ZERO)
                        .build())
                .extraHoursEnglishInternational(ExtraHours.builder()
                        .basicSubjects(BigDecimal.ZERO)
                        .majoringSubjects(BigDecimal.ZERO)
                        .build())
                .extraHoursMaster(BigDecimal.ZERO)
                .totalPayment(BigDecimal.ZERO)
                .build();
    }
}
