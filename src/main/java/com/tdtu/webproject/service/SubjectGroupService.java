package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtSubjectGroup;
import com.tdtu.webproject.repository.SubjectGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubjectGroupService {
    private final SubjectGroupRepository subjectGroupRepository;

    public List<TdtSubjectGroup> getAllSubjectGroup() {
        return subjectGroupRepository.getAllSubjectGroup();
    }

    public TdtSubjectGroup getSubjectGroupById(String groupId) {
        return subjectGroupRepository.getSubjectGroupById(groupId);
    }
}
