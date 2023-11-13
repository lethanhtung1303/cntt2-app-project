package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtChungChi;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtDiemHaiLong;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtGiangVien;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtQuaTrinhDaoTao;
import com.tdtu.webproject.model.condition.LecturerCondition;
import com.tdtu.webproject.repository.LecturerRepository;
import com.tdtu.webproject.utils.DateUtil;
import com.tdtu.webproject.utils.NumberUtil;
import generater.openapi.model.LecturerDetailResponse;
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
                .map(lecturer -> this.buildLecturerDetailResponse(lecturer, certificateMap.get(lecturer.getId()), trainingProcessMap.get(lecturer.getId()), satisfactionScoreMap.get(lecturer.getId())))
                .toList().stream()
                .sorted(Comparator
                        .comparing(LecturerDetailResponse::getCreatedAt)
                        .reversed())
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private LecturerDetailResponse buildLecturerDetailResponse(TdtGiangVien lecturer, List<TdtChungChi> certificateList, List<TdtQuaTrinhDaoTao> trainingProcessList, List<TdtDiemHaiLong> satisfactionScoreList) {
        
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
//                .certificate()
//                .trainingProcess()
//                .satisfactionScore()
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
                        .map(contact -> NumberUtil.toBigDeimal(contact).orElse(null))
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                .build();
    }
}
