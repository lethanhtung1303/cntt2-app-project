package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtMonHoc;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtNhomMon;
import com.tdtu.webproject.model.condition.SubjectCondition;
import com.tdtu.webproject.repository.SubjectGroupRepository;
import com.tdtu.webproject.repository.SubjectRepository;
import generater.openapi.model.Subject;
import generater.openapi.model.SubjectGroup;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectGroupRepository subjectGroupRepository;

    public List<TdtMonHoc> getAllSubject() {
        return subjectRepository.getAllSubject();
    }

    public Long count(String subjectIds) {
        SubjectCondition condition = this.buildSubjectCondition(subjectIds);
        return subjectRepository.countSubject(condition);
    }

    public List<Subject> find(String lecturerIds) {
        SubjectCondition condition = this.buildSubjectCondition(lecturerIds);
        List<TdtMonHoc> subjectList = subjectRepository.findSubject(condition);
        return Optional.ofNullable(subjectList).isPresent()
                ? subjectList.stream()
                .map(this::buildSubject)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private Subject buildSubject(TdtMonHoc subject) {
        TdtNhomMon subjectGroup = subjectGroupRepository.getSubjectGroupById(subject.getMaNhom());
        return Subject.builder()
                .maMon(subject.getMaMon())
                .subjectGroup(SubjectGroup.builder()
                        .maNhom(subjectGroup.getMaNhom())
                        .tenNhom(subjectGroup.getTenNhom())
                        .build())
                .tenMon(subject.getTenMon())
                .soTietLyThuyet(subject.getSoTietLyThuyet())
                .soTietThucHanh(subject.getSoTietThucHanh())
                .build();
    }

    private SubjectCondition buildSubjectCondition(String lecturerIds) {
        return SubjectCondition.builder()
                .subjectIds(Optional.ofNullable(lecturerIds).isPresent()
                        ? Arrays.stream(lecturerIds.split(","))
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                .build();
    }
}
