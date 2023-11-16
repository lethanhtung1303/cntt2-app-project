package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.*;
import com.tdtu.webproject.model.condition.LecturerCondition;
import com.tdtu.webproject.repository.LecturerRepository;
import com.tdtu.webproject.utils.DateUtil;
import com.tdtu.webproject.utils.NumberUtil;
import generater.openapi.model.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
    private final SubjectService subjectService;
    private final SubjectGroupService subjectGroupService;

    public Long count(String lecturerIds) {
        LecturerCondition condition = this.buildLecturerCondition(lecturerIds);
        return lecturerRepository.countLecturer(condition);
    }

    public List<LecturerDetailResponse> find(String lecturerIds) {
        List<TdtGiangVien> lecturerList = lecturerRepository.findLecturer(this.buildLecturerCondition(lecturerIds));

        List<TdtChungChi> certificateList = certificateService.findByLecturerId(lecturerIds);
        Map<BigDecimal, List<TdtChungChi>> certificateMap = certificateList.stream()
                .collect(Collectors.groupingBy(TdtChungChi::getGiangVienId));

        List<TdtQuaTrinhDaoTao> trainingProcessList = trainingProcessService.findByLecturerId(lecturerIds);
        Map<BigDecimal, List<TdtQuaTrinhDaoTao>> trainingProcessMap = trainingProcessList.stream()
                .collect(Collectors.groupingBy(TdtQuaTrinhDaoTao::getGiangVienId));

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
                                                               List<TdtQuaTrinhDaoTao> trainingProcessList,
                                                               List<TdtDiemHaiLong> satisfactionScoreList) {
        List<TdtNgonNguDaoTao> trainingLanguageList = trainingLanguageService.getAllTrainingLanguage();
        List<TdtNgonNgu> languageList = languageService.getAllLanguage();
        List<TdtTrinhDo> levelList = levelService.getAllLevel();
        List<TdtMonHoc> subjectList = subjectService.getAllSubject();

        Map<BigDecimal, List<Language>> trainingLanguageMap = trainingLanguageList.stream()
                .collect(Collectors.groupingBy(TdtNgonNguDaoTao::getQuaTrinhDaoTaoId,
                        Collectors.mapping(trainingLanguage -> getLanguageById(trainingLanguage.getNgonNguId(),
                                        languageList),
                                Collectors.toList())));

        Map<BigDecimal, TdtTrinhDo> levelMap = levelList.stream()
                .collect(Collectors.toMap(TdtTrinhDo::getId, level -> level));

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
                        .loaiTotNghiep(trainingProcess.getLoaiTotNghiep())
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
                        ? DateUtil.getValueFromLocalDateTime(lecturer.getBirthday(), DateUtil.DATETIME_FORMAT_SLASH)
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

    private LecturerCondition buildLecturerCondition(String lecturerIds) {
        return LecturerCondition.builder()
                .lecturerIds(Optional.ofNullable(lecturerIds).isPresent()
                        ? Arrays.stream(lecturerIds.split(","))
                        .map(lecturerId -> NumberUtil.toBigDeimal(lecturerId).orElse(null))
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                .build();
    }

    public Certificate buildCertificate(TdtChungChi certificateList) {
        return Certificate.builder()
                .id(certificateList.getId())
                .loaiChungChi(certificateList.getLoaiChungChi())
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
        return Subject.builder()
                .maMon(subject.getMaMon())
                .subjectGroup(SubjectGroup.builder()
                        .maNhom(subjectGroup.getMaNhom())
                        .tenNhom(subjectGroup.getTenNhom())
                        .build())
                .tenMon(subject.getTenMon())
                .soTietLyThuyet(subject.getSoTietLyThuyet())
                .soTietThucHanh(subject.getSoTietThucHanh())
                .build();
    }
}
