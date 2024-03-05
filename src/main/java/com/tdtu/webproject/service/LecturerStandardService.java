package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtLecturer;
import com.tdtu.webproject.mybatis.result.*;
import com.tdtu.webproject.repository.LecturerRepository;
import com.tdtu.webproject.utils.ArrayUtil;
import com.tdtu.webproject.utils.DateUtil;
import com.tdtu.webproject.utils.NumberUtil;
import generater.openapi.model.*;
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
        List<TdtLecturer> lecturerList = lecturerManageService.getAllLecturer();
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
                        lecturerTotalNumberLessonsMap.getOrDefault(lecturer.getId(), BigDecimal.ZERO),
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

        return NumberUtil.toBigDecimal(newSemesterStr).orElse(null);
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
        lecturer.setStandardDetail(this.setStandardDetail(trainingProcessList, trainingLanguage, totalNumberLessons, certificate, satisfactionScore));
        return lecturer;
    }

    private List<StandardDetail> setStandardDetail(List<LecturerTrainingProcessResult> trainingProcessList,
                                                   List<LecturerTrainingLanguageResult> trainingLanguage,
                                                   BigDecimal totalNumberLessons,
                                                   List<LecturerCertificateResult> certificate,
                                                   List<LecturerSatisfactionScoreResult> satisfactionScore) {

        Map<String, Object> matchingProcessAndLanguage = this.findMatchingProcessAndLanguage(trainingProcessList, trainingLanguage);
        TrainingProcessStandard trainingProcessStandard = TrainingProcessStandard.builder().build();
        if (matchingProcessAndLanguage.containsKey("trainingProcess") && matchingProcessAndLanguage.containsKey("matchedLanguageResult")) {
            LecturerTrainingProcessResult trainingProcess = (LecturerTrainingProcessResult) matchingProcessAndLanguage.get("trainingProcess");
            LecturerTrainingLanguageResult matchedLanguageResult = (LecturerTrainingLanguageResult) matchingProcessAndLanguage.get("matchedLanguageResult");
            trainingProcessStandard = TrainingProcessStandard.builder()
                    .school(trainingProcess.getSchool())
                    .majors(trainingProcess.getMajors())
                    .graduationYear(trainingProcess.getGraduationYear())
                    .graduationType(trainingProcess.getGraduationType())
                    .level(trainingProcess.getLevel())
                    .language(matchedLanguageResult.getLanguageName())
                    .build();
        }

        LecturerCertificateResult certificateStandard = this.findCertificate(certificate).orElse(null);

        LecturerTrainingProcessResult highestTrainingProcess = trainingProcessList.stream().min(Comparator.comparing(LecturerTrainingProcessResult::getDisplayOrder)).orElse(null);

        return List.of(StandardDetail.builder()
                .highestLevel(HighestLevel.builder()
                        .level(Optional.ofNullable(highestTrainingProcess).isPresent() ? highestTrainingProcess.getLevel() : null)
                        .graduationYear(Optional.ofNullable(highestTrainingProcess).isPresent() ? highestTrainingProcess.getGraduationYear() : null)
                        .graduationType(Optional.ofNullable(highestTrainingProcess).isPresent() ? highestTrainingProcess.getGraduationType() : null)
                        .build())
                .trainingProcessStandard(trainingProcessStandard)
                .totalNumberLessons(totalNumberLessons)
                .certificate(CertificateStandard.builder()
                        .certificateName(Optional.ofNullable(certificateStandard).isPresent() ? certificateStandard.getCertificateName() : null)
                        .certificateScore(Optional.ofNullable(certificateStandard).isPresent() ? certificateStandard.getCertificateScore() : null)
                        .build())
                .averageSatisfaction(BigDecimal.valueOf(this.getAverageScore(satisfactionScore)))
                .build());
    }

    private List<UniversityStandardDetailResponse> buildLecturerStandardDetailResponse(List<TdtLecturer> lecturerList) {
        return Optional.ofNullable(lecturerList).isPresent()
                ? lecturerList.stream()
                .map(this::buildLecturerStandardDetail)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private UniversityStandardDetailResponse buildLecturerStandardDetail(TdtLecturer lecturer) {
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

    private Map<String, Object> findMatchingProcessAndLanguage(List<LecturerTrainingProcessResult> trainingProcessList, List<LecturerTrainingLanguageResult> trainingLanguage) {
        Map<String, Object> result = new HashMap<>();

        Optional<LecturerTrainingProcessResult> matchedTrainingProcess = trainingProcessList.stream()
                .sorted(Comparator.comparing(LecturerTrainingProcessResult::getDisplayOrder))
                .filter(trainingProcess -> trainingProcess.getDisplayOrder().compareTo(BigDecimal.valueOf(LEVEL_MASTER)) <= 0)
                .filter(trainingProcess ->
                        trainingLanguage.stream()
                                .anyMatch(languageResult ->
                                        languageResult.getTrainingProcessId().compareTo(trainingProcess.getTrainingProcessId()) == 0
                                                && languageResult.getLanguageCode().compareTo(BigDecimal.valueOf(LANGUAGE_CODE_ENGLISH)) == 0
                                )
                )
                .findFirst();

        if (matchedTrainingProcess.isPresent()) {
            LecturerTrainingProcessResult trainingProcess = matchedTrainingProcess.get();

            LecturerTrainingLanguageResult matchedLanguageResult = trainingLanguage.stream()
                    .filter(languageResult ->
                            languageResult.getTrainingProcessId().compareTo(trainingProcess.getTrainingProcessId()) == 0
                                    && languageResult.getLanguageCode().compareTo(BigDecimal.valueOf(LANGUAGE_CODE_ENGLISH)) == 0
                    )
                    .findFirst()
                    .orElse(null);

            result.put("trainingProcess", trainingProcess);
            result.put("matchedLanguageResult", matchedLanguageResult);
        }

        return result;
    }

    private Optional<LecturerCertificateResult> findCertificate(List<LecturerCertificateResult> certificate) {
        return certificate.stream()
                .sorted(Comparator.comparing(LecturerCertificateResult::getCertificateCode))
                .filter(result -> {
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
                }).findFirst();
    }

    private boolean checkLanguage(List<LecturerTrainingProcessResult> trainingProcessList, List<LecturerTrainingLanguageResult> trainingLanguage, List<LecturerCertificateResult> certificate) {
        Map<String, Object> matchingProcessAndLanguage = this.findMatchingProcessAndLanguage(trainingProcessList, trainingLanguage);
        boolean languageCodeCondition = ArrayUtil.isNotNullAndNotEmptyList(trainingProcessList)
                && ArrayUtil.isNotNullAndNotEmptyList(trainingLanguage)
                && matchingProcessAndLanguage.containsKey("trainingProcess")
                && matchingProcessAndLanguage.containsKey("matchedLanguageResult");

        boolean certificateCondition = ArrayUtil.isNotNullAndNotEmptyList(certificate) && this.findCertificate(certificate).isPresent();

        return languageCodeCondition || certificateCondition;
    }

    private double getAverageScore(List<LecturerSatisfactionScoreResult> satisfactionScore) {
        return satisfactionScore.stream()
                .mapToDouble(result -> result.getSatisfactionScore().doubleValue())
                .average()
                .orElse(0.0);
    }

    private boolean checkSatisfactionScore(List<LecturerSatisfactionScoreResult> satisfactionScore) {
        if (!ArrayUtil.isNotNullAndNotEmptyList(satisfactionScore)) {
            return false;
        }
        return this.getAverageScore(satisfactionScore) >= 5.2;
    }

    public List<MasterStandardDetailResponse> getMasterStandards() {
        List<TdtLecturer> lecturerList = lecturerManageService.getAllLecturer();
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

    private List<MasterStandardDetailResponse> buildMasterStandardDetailResponse(List<TdtLecturer> lecturerList) {
        return Optional.ofNullable(lecturerList).isPresent()
                ? lecturerList.stream()
                .map(this::buildMasterStandardDetail)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private MasterStandardDetailResponse buildMasterStandardDetail(TdtLecturer lecturer) {
        return MasterStandardDetailResponse.builder()
                .id(lecturer.getId())
                .fullName(lecturer.getFirstName().concat(" ").concat(lecturer.getFullName()))
                .images(lecturer.getImages())
                .emailTdtu(lecturer.getEmailTdtu())
                .build();
    }
}
