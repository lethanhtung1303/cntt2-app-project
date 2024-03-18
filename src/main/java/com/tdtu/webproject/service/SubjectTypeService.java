package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtSubjectType;
import com.tdtu.webproject.repository.SubjectTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class SubjectTypeService {
    private final SubjectTypeRepository subjectTypeRepository;

    public List<TdtSubjectType> getAllSubjectType() {
        return subjectTypeRepository.getAllSubjectType();
    }

    public TdtSubjectType getSubjectTypeById(BigDecimal TypeId) {
        return subjectTypeRepository.getSubjectTypeById(TypeId);
    }
}
