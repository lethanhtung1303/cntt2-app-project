package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.*;
import com.tdtu.webproject.exception.BusinessException;
import com.tdtu.webproject.mybatis.condition.LecturerCondition;
import com.tdtu.webproject.mybatis.condition.LecturerRequest;
import com.tdtu.webproject.repository.LecturerRepository;
import com.tdtu.webproject.utils.*;
import generater.openapi.model.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.tdtu.webproject.constant.Const.*;

@Service
@AllArgsConstructor
public class LecturerService {
    private final LecturerRepository lecturerRepository;
    private final CertificateService certificateService;
    private final TrainingProcessService trainingProcessService;
    private final SatisfactionScoreService satisfactionScoreService;
    private final TrainingLanguageService trainingLanguageService;
    private final LanguageService languageService;
    private final LevelService levelService;
    private final GraduationTypeService graduationTypeService;
    private final SubjectService subjectService;
    private final SubjectGroupService subjectGroupService;
    private final SubjectTypeService subjectTypeService;
    private final CertificateTypeService certificateTypeService;
    private final ClassificationLecturerService classificationLecturerService;
    private final LecturerManageService lecturerManageService;
    private final ClassificationManageService classificationManageService;

    public Long count(String lecturerIds) {
        LecturerRequest request = LecturerRequest.builder()
                .lecturerIds(lecturerIds)
                .build();
        LecturerCondition condition = this.buildLecturerCondition(request);
        return lecturerRepository.countLecturer(condition);
    }

    public List<LecturerDetailResponse> find(String lecturerIds) {
        LecturerRequest request = LecturerRequest.builder()
                .lecturerIds(lecturerIds)
                .build();
        List<TdtLecturer> lecturerList = lecturerRepository.findLecturer(this.buildLecturerCondition(request));

        List<TdtCertificate> certificateList = certificateService.findByLecturerId(lecturerIds);
        Map<BigDecimal, List<TdtCertificate>> certificateMap = certificateList.stream()
                .collect(Collectors.groupingBy(TdtCertificate::getLecturerId));

        List<TdtQuaTrinhDaoTao> trainingProcessList = trainingProcessService.findByLecturerId(lecturerIds);
        Map<BigDecimal, List<TdtQuaTrinhDaoTao>> trainingProcessMap = trainingProcessList.stream()
                .collect(Collectors.groupingBy(TdtQuaTrinhDaoTao::getGiangVienId));

        List<TdtSatisfactoryScore> satisfactionScoreList = satisfactionScoreService.findByLecturerId(lecturerIds);
        Map<BigDecimal, List<TdtSatisfactoryScore>> satisfactionScoreMap = satisfactionScoreList.stream()
                .collect(Collectors.groupingBy(TdtSatisfactoryScore::getLecturerId));

        return Optional.ofNullable(lecturerList).isPresent()
                ? lecturerList.stream()
                .map(lecturer -> this.buildLecturerDetailResponse(lecturer,
                        certificateMap.getOrDefault(lecturer.getId(), Collections.emptyList()),
                        trainingProcessMap.getOrDefault(lecturer.getId(), Collections.emptyList()),
                        satisfactionScoreMap.getOrDefault(lecturer.getId(), Collections.emptyList())))
                .toList().stream()
                .sorted(Comparator
                        .comparing(LecturerDetailResponse::getCreatedAt)
                        .reversed())
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private LecturerDetailResponse buildLecturerDetailResponse(TdtLecturer lecturer,
                                                               List<TdtCertificate> certificateList,
                                                               List<TdtQuaTrinhDaoTao> trainingProcessList,
                                                               List<TdtSatisfactoryScore> satisfactionScoreList) {
        List<TdtTrainingLanguage> trainingLanguageList = trainingLanguageService.getAllTrainingLanguage();
        List<TdtLanguage> languageList = languageService.getAllLanguage();
        List<TdtDegree> levelList = levelService.getAllLevel();
        List<TdtLoaiTotNghiep> graduationTypeList = graduationTypeService.getAllGraduationType();
        List<TdtSubject> subjectList = subjectService.getAllSubject();
        List<TdtLecturerType> classificationLecturerList = classificationLecturerService.getAllClassification();

        Map<BigDecimal, TdtLecturerType> classificationLecturerMap = classificationLecturerList.stream()
                .collect(Collectors.toMap(TdtLecturerType::getTypeId, classification -> classification));

        Map<BigDecimal, List<Language>> trainingLanguageMap = trainingLanguageList.stream()
                .collect(Collectors.groupingBy(TdtTrainingLanguage::getTrainingProcessId,
                        Collectors.mapping(trainingLanguage -> getLanguageById(trainingLanguage.getLanguageId(),
                                        languageList),
                                Collectors.toList())));

        Map<BigDecimal, TdtDegree> levelMap = levelList.stream()
                .collect(Collectors.toMap(TdtDegree::getId, level -> level));

        Map<BigDecimal, TdtLoaiTotNghiep> graduationTypeMap = graduationTypeList.stream()
                .collect(Collectors.toMap(TdtLoaiTotNghiep::getId, graduationType -> graduationType));

        List<TrainingProcess> trainingProcesses = trainingProcessList.stream()
                .map(trainingProcess -> TrainingProcess.builder()
                        .id(trainingProcess.getId())
                        .level(Optional.ofNullable(levelMap.getOrDefault(trainingProcess.getTrinhDoId(), null)).isPresent()
                                ? this.buildLevel(levelMap.get(trainingProcess.getTrinhDoId()))
                                : null)
                        .language(trainingLanguageMap.getOrDefault(trainingProcess.getId(), Collections.emptyList()))
                        .truong(trainingProcess.getTruong())
                        .nganh(trainingProcess.getNganh())
                        .namTotNghiep(trainingProcess.getNamTotNghiep())
                        .deTaiTotNghiep(trainingProcess.getDeTaiTotNghiep())
                        .nguoiHuongDan(trainingProcess.getNguoiHuongDan())
                        .graduationType(Optional.ofNullable(graduationTypeMap.getOrDefault(trainingProcess.getLoaiTotNghiepId(), null)).isPresent()
                                ? GraduationType.builder()
                                .id(graduationTypeMap.get(trainingProcess.getLoaiTotNghiepId()).getId())
                                .loaiTotNghiep(graduationTypeMap.get(trainingProcess.getLoaiTotNghiepId()).getLoaiTotNghiep())
                                .build()
                                : null)
                        .build())
                .toList();

        Map<String, TdtSubject> subjectMap = subjectList.stream()
                .collect(Collectors.toMap(TdtSubject::getSubjectId, subject -> subject));

        List<SatisfactionScore> satisfactionScores = satisfactionScoreList.stream()
                .map(satisfactionScore -> SatisfactionScore.builder()
                        .id(satisfactionScore.getId())
                        .subject(this.buildSubject(subjectMap.getOrDefault(satisfactionScore.getSubjectId(), null)))
                        .hocKy(satisfactionScore.getSemester())
                        .diemHaiLong(satisfactionScore.getSatisfactoryScore())
                        .build())
                .toList();

        return LecturerDetailResponse.builder()
                .id(lecturer.getId())
                .firstName(lecturer.getFirstName())
                .fullName(lecturer.getFullName())
                .gender(lecturer.getGender())
                .images(lecturer.getImages())
                .birthday(Optional.ofNullable(lecturer.getBirthday()).isPresent()
                        ? DateUtil.getValueFromLocalDateTime(lecturer.getBirthday(), DateUtil.YYYYMMDD_FORMAT_HYPHEN)
                        : null)
                .placeOfBirth(lecturer.getPlaceOfBirth())
                .address(lecturer.getAddress())
                .addressTmp(lecturer.getAddressTmp())
                .phone(lecturer.getPhone())
                .email(lecturer.getEmail())
                .emailTdtu(lecturer.getEmailTdtu())
                .workplace(lecturer.getWorkplace())
                .mainPosition(lecturer.getMainPosition())
                .secondaryPosition(lecturer.getSecondaryPosition())
                .classificationLecturers(this.buildClassificationLecturers(classificationLecturerMap.getOrDefault(lecturer.getClassificationLecturers(), null)))
                .certificate(Optional.ofNullable(certificateList).isPresent()
                        ? certificateList.stream()
                        .map(this::buildCertificate)
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                .trainingProcess(trainingProcesses)
                .satisfactionScore(satisfactionScores)
                .isActive(lecturer.getIsActive())
                .createdAt(Optional.ofNullable(lecturer.getCreatedAt()).isPresent()
                        ? DateUtil.getValueFromLocalDateTime(lecturer.getCreatedAt(), DateUtil.DATETIME_FORMAT_SLASH)
                        : null)
                .createdBy(lecturer.getCreatedBy())
                .updatedAt(Optional.ofNullable(lecturer.getUpdatedAt()).isPresent()
                        ? DateUtil.getValueFromLocalDateTime(lecturer.getUpdatedAt(), DateUtil.DATETIME_FORMAT_SLASH)
                        : null)
                .updateBy(lecturer.getUpdateBy())
                .deletedFlag(lecturer.getDeletedFlag())
                .deletedAt(Optional.ofNullable(lecturer.getDeletedAt()).isPresent()
                        ? DateUtil.getValueFromLocalDateTime(lecturer.getDeletedAt(), DateUtil.DATETIME_FORMAT_SLASH)
                        : null)
                .deletedBy(lecturer.getDeletedBy())
                .build();
    }

    private LecturerCondition buildLecturerConditionForDelete(LecturerDeleteRequest request) {
        return LecturerCondition.builder()
                .lecturerIds(Optional.ofNullable(request.getLecturerIds()).isPresent()
                        ? Arrays.stream(request.getLecturerIds().split(","))
                        .map(lecturerId -> NumberUtil.toBigDecimal(lecturerId).orElse(null))
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                .deleteBy(request.getDeleteBy())
                .build();
    }

    private LecturerCondition buildLecturerCondition(LecturerRequest request) {
        return LecturerCondition.builder()
                .emailTdtu(request.getEmailTdtu())
                .lecturerIds(Optional.ofNullable(request.getLecturerIds()).isPresent()
                        ? Arrays.stream(request.getLecturerIds().split(","))
                        .map(lecturerId -> NumberUtil.toBigDecimal(lecturerId).orElse(null))
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                .build();
    }

    public Certificate buildCertificate(TdtCertificate certificateList) {
        TdtCertificateType certificateType = certificateTypeService.getCertificateTypeById(certificateList.getCertificateType());
        return Certificate.builder()
                .id(certificateList.getId())
                .certificateType(CertificateType.builder()
                        .maLoai(certificateType.getTypeId())
                        .tenLoai(certificateType.getType())
                        .build())
                .diem(certificateList.getGrade())
                .build();
    }

    public Language getLanguageById(BigDecimal languageId, List<TdtLanguage> languageList) {
        return languageList.stream()
                .filter(language -> language.getId().equals(languageId))
                .map(this::buildLanguage)
                .findFirst()
                .orElse(null);
    }

    public Language buildLanguage(TdtLanguage language) {
        return Language.builder()
                .id(language.getId())
                .tenNgonNgu(language.getLanguageName())
                .build();
    }

    public Level buildLevel(TdtDegree level) {
        return Level.builder()
                .id(level.getId())
                .trinhDo(level.getDegree())
                .kyHieu(level.getSignature())
                .displayOrder(level.getDisplayOrder())
                .build();
    }

    private Subject buildSubject(TdtSubject subject) {
        TdtSubjectGroup subjectGroup = subjectGroupService.getSubjectGroupById(subject.getSubjectId());
        TdtSubjectType subjectType = subjectTypeService.getSubjectTypeById(subject.getTypeId());
        return Subject.builder()
                .maMon(subject.getSubjectId())
                .phanLoai(subjectType.getType())
                .subjectGroup(SubjectGroup.builder()
                        .maNhom(subjectGroup.getGroupId())
                        .tenNhom(subjectGroup.getGroupName())
                        .build())
                .tenMon("[".concat(subjectType.getSignature()).concat("] ").concat(subject.getSubjectName()))
                .soTiet(subject.getTotalShift())
                .build();
    }

    private ClassificationLecturers buildClassificationLecturers(TdtLecturerType classification) {
        return ClassificationLecturers.builder()
                .maLoai(classification.getTypeId())
                .phanLoai(classification.getType())
                .build();
    }

    public String updateLecturer(BigDecimal lecturerId, LecturerUpdate lecturer, String updateBy) {
        if (Optional.ofNullable(lecturerId).isPresent()) {
            if (lecturerManageService.checkNotExistLecturer(lecturerId)) {
                throw new BusinessException("40001",
                        MessageProperties.getInstance().getProperty(LECTURER_NOT_FOUND, StringUtil.convertBigDecimalToString(lecturerId))
                );
            }
            if (!classificationManageService.checkExistClassification(lecturer.getClassification())) {
                throw new BusinessException("40002",
                        MessageProperties.getInstance().getProperty(INVALID_CLASSIFICATION)
                );
            }
            return lecturerRepository.update(this.buildTdtGiangVienForUpdate(lecturer, updateBy), lecturerId) > 0
                    ? SUCCESSFUL
                    : FAIL;
        }
        return FAIL;
    }

    private TdtLecturer buildTdtGiangVienForUpdate(LecturerUpdate lecturer, String updateBy) {
        return TdtLecturer.builder()
                .firstName(lecturer.getFirstName())
                .fullName(lecturer.getFullName())
                .gender(lecturer.getGender())
                .images(lecturer.getImages())
                .birthday(DateUtil.parseLocalDateTime(DateUtil.parseLocalDate(lecturer.getBirthday(), DateUtil.YYYYMMDD_FORMAT_HYPHEN)))
                .placeOfBirth(lecturer.getPlaceOfBirth())
                .address(lecturer.getAddress())
                .addressTmp(lecturer.getAddressTmp())
                .phone(lecturer.getPhone())
                .email(lecturer.getEmail())
                .emailTdtu(lecturer.getEmailTdtu())
                .workplace(lecturer.getWorkplace())
                .mainPosition(lecturer.getMainPosition())
                .secondaryPosition(lecturer.getSecondaryPosition())
                .classificationLecturers(lecturer.getClassification())
                .updateBy(updateBy)
                .updatedAt(DateUtil.getTimeNow())
                .build();
    }

    public String deleteLecturer(LecturerDeleteRequest request) {
        LecturerCondition condition = this.buildLecturerConditionForDelete(request);
        if (!ArrayUtil.isNotNullAndNotEmptyList(condition.getLecturerIds())) {
            throw new BusinessException("40001",
                    MessageProperties.getInstance().getProperty(DELETED_LECTURERS_EMPTY)
            );
        }
        return lecturerRepository.delete(condition) > 0
                ? SUCCESSFUL
                : FAIL;
    }

    public String createLecturer(LecturerCreate lecturer, String createBy) {
        if (!classificationManageService.checkExistClassification(lecturer.getClassification())) {
            throw new BusinessException("40001",
                    MessageProperties.getInstance().getProperty(INVALID_CLASSIFICATION)
            );
        }
        LecturerRequest request = LecturerRequest.builder()
                .emailTdtu(lecturer.getEmailTdtu())
                .build();
        List<TdtLecturer> lecturerList = lecturerRepository.findLecturer(this.buildLecturerCondition(request));
        if (ArrayUtil.isNotNullAndNotEmptyList(lecturerList)) {
            throw new BusinessException("40002",
                    MessageProperties.getInstance().getProperty(LECTURER_ALREADY)
            );
        }
        return lecturerRepository.create(this.buildTdtGiangVienForCreate(lecturer, createBy)) > 0
                ? SUCCESSFUL
                : FAIL;
    }

    private TdtLecturer buildTdtGiangVienForCreate(LecturerCreate lecturer, String createBy) {
        return TdtLecturer.builder()
                .firstName(lecturer.getFirstName())
                .fullName(lecturer.getFullName())
                .gender(lecturer.getGender())
                .images(lecturer.getImages())
                .birthday(DateUtil.parseLocalDateTime(DateUtil.parseLocalDate(lecturer.getBirthday(), DateUtil.YYYYMMDD_FORMAT_HYPHEN)))
                .placeOfBirth(lecturer.getPlaceOfBirth())
                .address(lecturer.getAddress())
                .addressTmp(lecturer.getAddressTmp())
                .phone(lecturer.getPhone())
                .email(lecturer.getEmail())
                .emailTdtu(lecturer.getEmailTdtu())
                .workplace(lecturer.getWorkplace())
                .mainPosition(lecturer.getMainPosition())
                .secondaryPosition(lecturer.getSecondaryPosition())
                .classificationLecturers(lecturer.getClassification())
                .createdBy(createBy)
                .createdAt(DateUtil.getTimeNow())
                .build();
    }
}
