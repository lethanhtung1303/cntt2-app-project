package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtNhomMon;
import com.tdtu.webproject.repository.SubjectGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubjectGroupService {
    private final SubjectGroupRepository subjectGroupRepository;

    public List<TdtNhomMon> getAllSubjectGroup() {
        return subjectGroupRepository.getAllSubjectGroup();
    }
}
