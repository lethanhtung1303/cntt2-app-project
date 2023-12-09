package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtLoaiMon;
import com.tdtu.webproject.repository.SubjectTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class SubjectTypeService {
    private final SubjectTypeRepository subjectTypeRepository;

    public List<TdtLoaiMon> getAllSubjectType() {
        return subjectTypeRepository.getAllSubjectType();
    }

    public TdtLoaiMon getSubjectTypeById(BigDecimal TypeId) {
        return subjectTypeRepository.getSubjectTypeById(TypeId);
    }
}
