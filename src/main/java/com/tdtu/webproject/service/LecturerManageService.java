package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtChungChi;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtDiemHaiLong;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtGiangVien;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtQuaTrinhDaoTao;
import com.tdtu.webproject.model.condition.LecturerCondition;
import com.tdtu.webproject.repository.LecturerRepository;
import com.tdtu.webproject.utils.ArrayUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LecturerManageService {

    private final LecturerRepository lecturerRepository;

    public List<TdtChungChi> getAllCertificateOfLecturer(BigDecimal lecturerId, List<TdtChungChi> certificateList) {
        return certificateList.stream()
                .filter(certificate -> certificate.getGiangVienId().equals(lecturerId))
                .collect(Collectors.toList());
    }

    public List<TdtQuaTrinhDaoTao> getAllTrainingProcessOfLecturer(BigDecimal lecturerId, List<TdtQuaTrinhDaoTao> trainingProcessList) {
        return trainingProcessList.stream()
                .filter(trainingProcess -> trainingProcess.getGiangVienId().equals(lecturerId))
                .collect(Collectors.toList());
    }

    public List<TdtDiemHaiLong> getAllSatisfactionScoreListOfLecturer(BigDecimal lecturerId, List<TdtDiemHaiLong> satisfactionScoreList) {
        return satisfactionScoreList.stream()
                .filter(satisfactionScore -> satisfactionScore.getGiangVienId().equals(lecturerId))
                .collect(Collectors.toList());
    }

    public boolean checkExistLecturer(BigDecimal lecturerId) {
        List<TdtGiangVien> lecturerList = lecturerRepository
                .findLecturer(
                        LecturerCondition.builder()
                                .lecturerIds(List.of(lecturerId))
                                .build());
        return ArrayUtil.isNotNullAndNotEmptyList(lecturerList);
    }
}
