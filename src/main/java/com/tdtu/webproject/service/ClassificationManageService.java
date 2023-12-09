package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtLoaiGiangVien;
import com.tdtu.webproject.repository.ClassificationLecturerRepository;
import com.tdtu.webproject.utils.ArrayUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class ClassificationManageService {
    private final ClassificationLecturerRepository classificationLecturerRepository;

    public List<TdtLoaiGiangVien> getAllClassification() {
        return classificationLecturerRepository.getAllClassification();
    }

    public boolean checkExistClassification(BigDecimal classificationId) {
        List<TdtLoaiGiangVien> results = this.getAllClassification().stream()
                .filter(classification -> classification.getMaLoai().equals(classificationId)).toList();
        return ArrayUtil.isNotNullAndNotEmptyList(results);
    }
}
