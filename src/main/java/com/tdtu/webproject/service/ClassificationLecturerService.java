package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtLecturerType;
import com.tdtu.webproject.repository.ClassificationLecturerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClassificationLecturerService {
    private final ClassificationLecturerRepository classificationLecturerRepository;

    public List<TdtLecturerType> getAllClassification() {
        return classificationLecturerRepository.getAllClassification();
    }
}
