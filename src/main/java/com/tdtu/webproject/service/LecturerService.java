package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtChungChi;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtDiemHaiLong;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtGiangVien;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtQuaTrinhDaoTao;
import com.tdtu.webproject.model.condition.LecturerCondition;
import com.tdtu.webproject.repository.LecturerRepository;
import com.tdtu.webproject.utils.NumberUtil;
import generater.openapi.model.LecturerDetailResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
        LecturerCondition condition = this.buildLecturerCondition(lecturerIds);
        List<TdtGiangVien> lecturerList = lecturerRepository.findLecturer(this.buildLecturerCondition(lecturerIds));
        List<TdtChungChi> certificateList = certificateService.findByLecturerId(lecturerIds);
        List<TdtQuaTrinhDaoTao> trainingProcessList = trainingProcessService.findByLecturerId(lecturerIds);
        List<TdtDiemHaiLong> satisfactionScoreList = satisfactionScoreService.findByLecturerId(lecturerIds);

        return Collections.emptyList();
    }

    private LecturerDetailResponse buildLecturerDetailResponse() {
        return LecturerDetailResponse.builder()

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
