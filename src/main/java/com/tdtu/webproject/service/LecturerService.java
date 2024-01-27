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
        List<TdtGiangVien> lecturerList = lecturerRepository.findLecturer(this.buildLecturerCondition(request));

        List<TdtChungChi> certificateList = certificateService.findByLecturerId(lecturerIds);
        Map<BigDecimal, List<TdtChungChi>> certificateMap = certificateList.stream()
                .collect(Collectors.groupingBy(TdtChungChi::getGiangVienId));

        List<TdtTrainingProcess> trainingProcessList = trainingProcessService.findByLecturerId(lecturerIds);
        Map<BigDecimal, List<TdtTrainingProcess>> trainingProcessMap = trainingProcessList.stream()
                .collect(Collectors.groupingBy(TdtTrainingProcess::getLecturerId));

        List<TdtDiemHaiLong> satisfactionScoreList = satisfactionScoreService.findByLecturerId(lecturerIds);
        Map<BigDecimal, List<TdtDiemHaiLong>> satisfactionScoreMap = satisfactionScoreList.stream()
                .collect(Collectors.groupingBy(TdtDiemHaiLong::getGiangVienId));

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

    private LecturerDetailResponse buildLecturerDetailResponse(TdtGiangVien lecturer,
                                                               List<TdtChungChi> certificateList,
                                                               List<TdtTrainingProcess> trainingProcessList,
                                                               List<TdtDiemHaiLong> satisfactionScoreList) {
        List<TdtNgonNguDaoTao> trainingLanguageList = trainingLanguageService.getAllTrainingLanguage();
        List<TdtNgonNgu> languageList = languageService.getAllLanguage();
        List<TdtTrinhDo> levelList = levelService.getAllLevel();
        List<TdtGraduationType> graduationTypeList = graduationTypeService.getAllGraduationType();
        List<TdtMonHoc> subjectList = subjectService.getAllSubject();
        List<TdtLoaiGiangVien> classificationLecturerList = classificationLecturerService.getAllClassification();

        Map<BigDecimal, TdtLoaiGiangVien> classificationLecturerMap = classificationLecturerList.stream()
                .collect(Collectors.toMap(TdtLoaiGiangVien::getMaLoai, classification -> classification));

        Map<BigDecimal, List<Language>> trainingLanguageMap = trainingLanguageList.stream()
                .collect(Collectors.groupingBy(TdtNgonNguDaoTao::getQuaTrinhDaoTaoId,
                        Collectors.mapping(trainingLanguage -> getLanguageById(trainingLanguage.getNgonNguId(),
                                        languageList),
                                Collectors.toList())));

        Map<BigDecimal, TdtTrinhDo> levelMap = levelList.stream()
                .collect(Collectors.toMap(TdtTrinhDo::getId, level -> level));

        Map<BigDecimal, TdtGraduationType> graduationTypeMap = graduationTypeList.stream()
                .collect(Collectors.toMap(TdtGraduationType::getId, graduationType -> graduationType));

        List<TrainingProcess> trainingProcesses = trainingProcessList.stream()
                .map(trainingProcess -> TrainingProcess.builder()
                        .id(trainingProcess.getId())
                        .level(Optional.ofNullable(levelMap.getOrDefault(trainingProcess.getQualificationId(), null)).isPresent()
                                ? this.buildLevel(levelMap.get(trainingProcess.getQualificationId()))
                                : null)
                        .language(trainingLanguageMap.getOrDefault(trainingProcess.getId(), Collections.emptyList()))
                        .truong(trainingProcess.getUniversity())
                        .nganh(trainingProcess.getMajor())
                        .namTotNghiep(trainingProcess.getGraduationYear())
                        .deTaiTotNghiep(trainingProcess.getThesisTitle())
                        .nguoiHuongDan(trainingProcess.getInstructor())
                        .graduationType(Optional.ofNullable(graduationTypeMap.getOrDefault(trainingProcess.getGraduationTypeId(),
                                null)).isPresent()
                                ? GraduationType.builder()
                                .id(graduationTypeMap.get(trainingProcess.getGraduationTypeId()).getId())
                                .loaiTotNghiep(graduationTypeMap.get(trainingProcess.getGraduationTypeId()).getGraduationType())
                                .build()
                                : null)
                        .build())
                .toList();

        Map<String, TdtMonHoc> subjectMap = subjectList.stream()
                .collect(Collectors.toMap(TdtMonHoc::getMaMon, subject -> subject));

        List<SatisfactionScore> satisfactionScores = satisfactionScoreList.stream()
                .map(satisfactionScore -> SatisfactionScore.builder()
                        .id(satisfactionScore.getId())
                        .subject(this.buildSubject(subjectMap.getOrDefault(satisfactionScore.getMaMon(), null)))
                        .hocKy(satisfactionScore.getHocKy())
                        .diemHaiLong(satisfactionScore.getDiemHaiLong())
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

    public Certificate buildCertificate(TdtChungChi certificateList) {
        TdtLoaiChungChi certificateType = certificateTypeService.getCertificateTypeById(certificateList.getLoaiChungChi());
        return Certificate.builder()
                .id(certificateList.getId())
                .certificateType(CertificateType.builder()
                        .maLoai(certificateType.getMaLoai())
                        .tenLoai(certificateType.getPhanLoai())
                        .build())
                .diem(certificateList.getDiem())
                .build();
    }

    public Language getLanguageById(BigDecimal languageId, List<TdtNgonNgu> languageList) {
        return languageList.stream()
                .filter(language -> language.getId().equals(languageId))
                .map(this::buildLanguage)
                .findFirst()
                .orElse(null);
    }

    public Language buildLanguage(TdtNgonNgu language) {
        return Language.builder()
                .id(language.getId())
                .tenNgonNgu(language.getTenNgonNgu())
                .build();
    }

    public Level buildLevel(TdtTrinhDo level) {
        return Level.builder()
                .id(level.getId())
                .trinhDo(level.getTrinhDo())
                .kyHieu(level.getKyHieu())
                .displayOrder(level.getDisplayOrder())
                .build();
    }

    private Subject buildSubject(TdtMonHoc subject) {
        TdtNhomMon subjectGroup = subjectGroupService.getSubjectGroupById(subject.getMaNhom());
        TdtLoaiMon subjectType = subjectTypeService.getSubjectTypeById(subject.getMaLoai());
        return Subject.builder()
                .maMon(subject.getMaMon())
                .phanLoai(subjectType.getPhanLoai())
                .subjectGroup(SubjectGroup.builder()
                        .maNhom(subjectGroup.getMaNhom())
                        .tenNhom(subjectGroup.getTenNhom())
                        .build())
                .tenMon("[".concat(subjectType.getKyHieu()).concat("] ").concat(subject.getTenMon()))
                .soTiet(subject.getSoTiet())
                .build();
    }

    private ClassificationLecturers buildClassificationLecturers(TdtLoaiGiangVien classification) {
        return ClassificationLecturers.builder()
                .maLoai(classification.getMaLoai())
                .phanLoai(classification.getPhanLoai())
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

    private TdtGiangVien buildTdtGiangVienForUpdate(LecturerUpdate lecturer, String updateBy) {
        return TdtGiangVien.builder()
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
        List<TdtGiangVien> lecturerList = lecturerRepository.findLecturer(this.buildLecturerCondition(request));
        if (ArrayUtil.isNotNullAndNotEmptyList(lecturerList)) {
            throw new BusinessException("40002",
                    MessageProperties.getInstance().getProperty(LECTURER_ALREADY)
            );
        }
        return lecturerRepository.create(this.buildTdtGiangVienForCreate(lecturer, createBy)) > 0
                ? SUCCESSFUL
                : FAIL;
    }

    private TdtGiangVien buildTdtGiangVienForCreate(LecturerCreate lecturer, String createBy) {
        return TdtGiangVien.builder()
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
