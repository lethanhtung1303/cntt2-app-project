package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtLecturerType;
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

    public List<TdtLecturerType> getAllClassification() {
        return classificationLecturerRepository.getAllClassification();
    }

    public boolean checkExistClassification(BigDecimal classificationId) {
        List<TdtLecturerType> results = this.getAllClassification().stream()
                .filter(classification -> classification.getTypeId().equals(classificationId)).toList();
        return ArrayUtil.isNotNullAndNotEmptyList(results);
    }
}
