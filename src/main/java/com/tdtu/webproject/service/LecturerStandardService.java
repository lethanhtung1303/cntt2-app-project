package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtGiangVien;
import com.tdtu.webproject.mybatis.result.*;
import com.tdtu.webproject.repository.LecturerRepository;
import com.tdtu.webproject.utils.ArrayUtil;
import com.tdtu.webproject.utils.DateUtil;
import com.tdtu.webproject.utils.NumberUtil;
import generater.openapi.model.MasterStandardDetailResponse;
import generater.openapi.model.UniversityStandardDetailResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.tdtu.webproject.constant.Const.*;

@Service
@AllArgsConstructor
public class LecturerStandardService {

    private final LecturerManageService lecturerManageService;
    private final LecturerRepository lecturerRepository;

    public List<UniversityStandardDetailResponse> getUniversityStandards(BigDecimal semester) {
        List<TdtGiangVien> lecturerList = lecturerManageService.getAllLecturer();
        List<UniversityStandardDetailResponse> lecturerStandardRawData = this.buildLecturerStandardDetailResponse(lecturerList);

        List<LecturerTrainingProcessResult> trainingProcessList = lecturerRepository.getLecturerTrainingProcess();
        List<LecturerTeachingResult> teachingList = lecturerRepository.getLecturerTeaching();
        List<LecturerTrainingLanguageResult> trainingLanguageList = lecturerRepository.getLecturerTrainingLanguage();
        List<LecturerCertificateResult> certificateList = lecturerRepository.getLecturerCertificate();
        List<LecturerSatisfactionScoreResult> satisfactionScoreList =
                lecturerRepository.getLecturerSatisfactionScore(this.getPreviousSemester(semester));

        Map<BigDecimal, List<LecturerTrainingProcessResult>> lecturerTrainingProcessMap = trainingProcessList.stream()
                .collect(Collectors.groupingBy(LecturerTrainingProcessResult::getLecturerId, Collectors.toList()));
        Map<BigDecimal, BigDecimal> lecturerTotalNumberLessonsMap = teachingList.stream()
                .collect(Collectors.groupingBy(LecturerTeachingResult::getLecturerId,
                        Collectors.mapping(LecturerTeachingResult::getNumberLessons,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
        Map<BigDecimal, List<LecturerTrainingLanguageResult>> trainingLanguageMap = trainingLanguageList.stream()
                .collect(Collectors.groupingBy(LecturerTrainingLanguageResult::getLecturerId, Collectors.toList()));
        Map<BigDecimal, List<LecturerCertificateResult>> certificateMap = certificateList.stream()
                .collect(Collectors.groupingBy(LecturerCertificateResult::getLecturerId, Collectors.toList()));
        Map<BigDecimal, List<LecturerSatisfactionScoreResult>> satisfactionScoreMap = satisfactionScoreList.stream()
                .collect(Collectors.groupingBy(LecturerSatisfactionScoreResult::getLecturerId, Collectors.toList()));

        return Optional.ofNullable(lecturerStandardRawData).isPresent()
                ? lecturerStandardRawData.stream()
                .map(lecturer -> this.buildResponse(lecturer,
                        lecturerTrainingProcessMap.getOrDefault(lecturer.getId(), Collections.emptyList()),
                        lecturerTotalNumberLessonsMap.getOrDefault(lecturer.getId(), null),
                        trainingLanguageMap.getOrDefault(lecturer.getId(), Collections.emptyList()),
                        certificateMap.getOrDefault(lecturer.getId(), Collections.emptyList()),
                        satisfactionScoreMap.getOrDefault(lecturer.getId(), Collections.emptyList())))
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    public BigDecimal getPreviousSemester(BigDecimal semester) {
        String semesterStr = semester.toString();
        String yearStr = semesterStr.substring(0, semesterStr.length() - 2);
        String monthStr = semesterStr.substring(semesterStr.length() - 2);
        int year = Integer.parseInt(yearStr);
        int month = Integer.parseInt(monthStr);
        if (month == 1) {
            month = 3;
            year--;
        } else if (month == 2 || month == 3) {
            month--;
        }

        String newSemesterStr = String.format("%04d%02d", year, month);

        return NumberUtil.toBigDecimal(newSemesterStr).get();
    }

    private UniversityStandardDetailResponse buildResponse(UniversityStandardDetailResponse lecturer,
                                                           List<LecturerTrainingProcessResult> trainingProcessList,
                                                           BigDecimal totalNumberLessons,
                                                           List<LecturerTrainingLanguageResult> trainingLanguage,
                                                           List<LecturerCertificateResult> certificate,
                                                           List<LecturerSatisfactionScoreResult> satisfactionScore) {
        lecturer.setIsTeachingTheory(this.checkTeachingTheory(trainingProcessList, totalNumberLessons));
        lecturer.setIsTeachingPractice(this.checkTeachingPractice(trainingProcessList));
        lecturer.setIsTeachingVietnamese(this.checkHighQualityInspection(trainingProcessList, totalNumberLessons));
        lecturer.setIsTeachingEnglish(this.checkHighQualityInspection(trainingProcessList, totalNumberLessons)
                && this.checkTeachingEnglish(trainingProcessList, trainingLanguage, certificate, satisfactionScore));
        lecturer.setIsEnglishInternational(lecturer.getIsTeachingEnglish());
        return lecturer;
    }

    private List<UniversityStandardDetailResponse> buildLecturerStandardDetailResponse(List<TdtGiangVien> lecturerList) {
        return Optional.ofNullable(lecturerList).isPresent()
                ? lecturerList.stream()
                .map(this::buildLecturerStandardDetail)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private UniversityStandardDetailResponse buildLecturerStandardDetail(TdtGiangVien lecturer) {
        return UniversityStandardDetailResponse.builder()
                .id(lecturer.getId())
                .fullName(lecturer.getFirstName().concat(" ").concat(lecturer.getFullName()))
                .images(lecturer.getImages())
                .emailTdtu(lecturer.getEmailTdtu())
                .isTeachingTheory(false)
                .isTeachingPractice(false)
                .isTeachingVietnamese(false)
                .isTeachingEnglish(false)
                .isEnglishInternational(false)
                .build();
    }

    private Boolean checkTeachingTheory(List<LecturerTrainingProcessResult> trainingProcessList, BigDecimal totalNumberLessons) {
        return ArrayUtil.isNotNullAndNotEmptyList(trainingProcessList) && trainingProcessList.stream()
                .anyMatch(trainingProcess ->
                        (trainingProcess.getDisplayOrder() != null && trainingProcess.getDisplayOrder().compareTo(BigDecimal.valueOf(4)) <= 0)
                                && (totalNumberLessons != null && totalNumberLessons.compareTo(BigDecimal.valueOf(200)) >= 0)
                );
    }

    private Boolean checkTeachingPractice(List<LecturerTrainingProcessResult> trainingProcessList) {
        return ArrayUtil.isNotNullAndNotEmptyList(trainingProcessList) && trainingProcessList.stream()
                .anyMatch(trainingProcess ->
                        (trainingProcess.getDisplayOrder() != null && trainingProcess.getDisplayOrder().compareTo(BigDecimal.valueOf(4)) <= 0)
                                || (trainingProcess.getGraduationTypeCode() != null && trainingProcess.getGraduationTypeCode().compareTo(BigDecimal.valueOf(3)) <= 0)
                );
    }

    private Boolean checkHighQualityInspection(List<LecturerTrainingProcessResult> trainingProcessList, BigDecimal totalNumberLessons) {
        return ArrayUtil.isNotNullAndNotEmptyList(trainingProcessList) && trainingProcessList.stream()
                .anyMatch(trainingProcess ->
                        (trainingProcess.getDisplayOrder() != null && trainingProcess.getDisplayOrder().compareTo(BigDecimal.valueOf(4)) <= 0)
                                && (totalNumberLessons != null && totalNumberLessons.compareTo(BigDecimal.valueOf(200)) >= 0)
                );
    }

    private boolean checkTeachingEnglish(List<LecturerTrainingProcessResult> trainingProcessList,
                                         List<LecturerTrainingLanguageResult> trainingLanguage,
                                         List<LecturerCertificateResult> certificate,
                                         List<LecturerSatisfactionScoreResult> satisfactionScore) {
        return this.checkLanguage(trainingProcessList, trainingLanguage, certificate) && this.checkSatisfactionScore(satisfactionScore);
    }

    private boolean checkLanguage(List<LecturerTrainingProcessResult> trainingProcessList, List<LecturerTrainingLanguageResult> trainingLanguage, List<LecturerCertificateResult> certificate) {
        boolean languageCodeCondition = ArrayUtil.isNotNullAndNotEmptyList(trainingProcessList)
                && ArrayUtil.isNotNullAndNotEmptyList(trainingLanguage)
                && trainingProcessList.stream()
                .filter(trainingProcess -> trainingProcess.getDisplayOrder().compareTo(BigDecimal.valueOf(LEVEL_MASTER)) <= 0)
                .anyMatch(trainingProcess ->
                        trainingLanguage.stream()
                                .anyMatch(languageResult ->
                                        languageResult.getTrainingProcessId().compareTo(trainingProcess.getTrainingProcessId()) == 0
                                                && languageResult.getLanguageCode().compareTo(BigDecimal.valueOf(LANGUAGE_CODE_ENGLISH)) == 0
                                )
                );

        boolean certificateCondition = ArrayUtil.isNotNullAndNotEmptyList(certificate) && certificate.stream()
                .anyMatch(result -> {
                    BigDecimal score = NumberUtil.toBigDecimal(result.getCertificateScore()).orElse(BigDecimal.ZERO);
                    BigDecimal threshold;

                    switch (result.getCertificateCode().intValue()) {
                        case 1:
                            threshold = NumberUtil.toBigDecimal(STANDARD_IELTS).orElse(BigDecimal.ZERO);
                            break;
                        case 2:
                            threshold = NumberUtil.toBigDecimal(STANDARD_TOEFL_IBT).orElse(BigDecimal.ZERO);
                            break;
                        case 3:
                            threshold = NumberUtil.toBigDecimal(STANDARD_TOEIC).orElse(BigDecimal.ZERO);
                            break;
                        case 4:
                            threshold = NumberUtil.toBigDecimal(STANDARD_CAMBRIDGE).orElse(BigDecimal.ZERO);
                            break;
                        case 5:
                            threshold = NumberUtil.toBigDecimal(STANDARD_APTIS_ESOL).orElse(BigDecimal.ZERO);
                            break;
                        default:
                            return false;
                    }

                    return score.compareTo(threshold) >= 0;
                });

        return languageCodeCondition || certificateCondition;
    }

    private boolean checkSatisfactionScore(List<LecturerSatisfactionScoreResult> satisfactionScore) {
        if (!ArrayUtil.isNotNullAndNotEmptyList(satisfactionScore)) {
            return false;
        }
        OptionalDouble averageScore = satisfactionScore.stream()
                .mapToDouble(result -> result.getSatisfactionScore().doubleValue())
                .average();
        return averageScore.orElse(0.0) >= 5.2;
    }

    public List<MasterStandardDetailResponse> getMasterStandards() {
        List<TdtGiangVien> lecturerList = lecturerManageService.getAllLecturer();
        List<MasterStandardDetailResponse> masterStandardRawData = this.buildMasterStandardDetailResponse(lecturerList);

        List<LecturerTrainingProcessResult> trainingProcessList = lecturerRepository.getLecturerTrainingProcess();
        Map<BigDecimal, List<LecturerTrainingProcessResult>> lecturerTrainingProcessMap = trainingProcessList.stream()
                .collect(Collectors.groupingBy(LecturerTrainingProcessResult::getLecturerId, Collectors.toList()));

        return this.checkMasterStandards(masterStandardRawData, lecturerTrainingProcessMap);
    }

    private List<MasterStandardDetailResponse> checkMasterStandards(List<MasterStandardDetailResponse> masterStandardRawData, Map<BigDecimal, List<LecturerTrainingProcessResult>> lecturerTrainingProcessMap) {
        int currentYear = DateUtil.getTimeNow().getYear();
        return masterStandardRawData.stream()
                .filter(master -> {
                    List<LecturerTrainingProcessResult> matchingResults =
                            lecturerTrainingProcessMap.getOrDefault(master.getId(), Collections.emptyList()).stream()
                                    .filter(result -> (result.getDisplayOrder().intValue() <= 3) && ((currentYear - result.getGraduationYear().intValue()) >= 2))
                                    .toList();
                    return !matchingResults.isEmpty();
                })
                .collect(Collectors.toList());
    }

    private List<MasterStandardDetailResponse> buildMasterStandardDetailResponse(List<TdtGiangVien> lecturerList) {
        return Optional.ofNullable(lecturerList).isPresent()
                ? lecturerList.stream()
                .map(this::buildMasterStandardDetail)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private MasterStandardDetailResponse buildMasterStandardDetail(TdtGiangVien lecturer) {
        return MasterStandardDetailResponse.builder()
                .id(lecturer.getId())
                .fullName(lecturer.getFirstName().concat(" ").concat(lecturer.getFullName()))
                .images(lecturer.getImages())
                .emailTdtu(lecturer.getEmailTdtu())
                .build();
    }
}
