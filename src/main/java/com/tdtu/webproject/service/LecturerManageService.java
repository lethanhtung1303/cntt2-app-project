package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtChungChi;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtDiemHaiLong;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtQuaTrinhDaoTao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LecturerManageService {
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
}
