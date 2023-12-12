package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtLoaiMon;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtMonHoc;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtNhomMon;
import com.tdtu.webproject.exception.BusinessException;
import com.tdtu.webproject.model.condition.SubjectCondition;
import com.tdtu.webproject.model.condition.SubjectGroupCondition;
import com.tdtu.webproject.repository.SubjectGroupRepository;
import com.tdtu.webproject.repository.SubjectRepository;
import com.tdtu.webproject.repository.SubjectTypeRepository;
import com.tdtu.webproject.utils.ArrayUtil;
import com.tdtu.webproject.utils.DateUtil;
import generater.openapi.model.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tdtu.webproject.constant.Const.FAIL;
import static com.tdtu.webproject.constant.Const.SUCCESSFUL;

@Service
@AllArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectGroupRepository subjectGroupRepository;
    private final SubjectTypeRepository subjectTypeRepository;
    private final SubjectManageService subjectManageService;

    public List<TdtMonHoc> getAllSubject() {
        return subjectRepository.getAllSubject();
    }

    public Long count(String subjectIds) {
        SubjectCondition condition = this.buildSubjectCondition(subjectIds);
        return subjectRepository.countSubject(condition);
    }

    public List<Subject> find(String subjectIds) {
        SubjectCondition condition = this.buildSubjectCondition(subjectIds);
        List<TdtMonHoc> subjectList = subjectRepository.findSubject(condition);
        return Optional.ofNullable(subjectList).isPresent()
                ? subjectList.stream()
                .map(this::buildSubject)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private Subject buildSubject(TdtMonHoc subject) {
        TdtNhomMon subjectGroup = subjectGroupRepository.getSubjectGroupById(subject.getMaNhom());
        TdtLoaiMon subjectType = subjectTypeRepository.getSubjectTypeById(subject.getMaLoai());
        return Subject.builder()
                .maMon(subject.getMaMon())
                .phanLoai(subjectType.getPhanLoai())
                .subjectGroup(SubjectGroup.builder()
                        .maNhom(subjectGroup.getMaNhom())
                        .tenNhom(subjectGroup.getTenNhom())
                        .build())
                .tenMon("[".concat(subjectType.getKyHieu()).concat("] ").concat(subject.getTenMon()))
                .soTiet(subject.getSoTiet())
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

    public Long countSubjectGroup(String groupIds) {
        SubjectGroupCondition condition = this.buildSubjectGroupCondition(groupIds);
        return subjectGroupRepository.countSubjectGroup(condition);
    }

    public List<SubjectGroup> findSubjectGroup(String groupIds) {
        SubjectGroupCondition condition = this.buildSubjectGroupCondition(groupIds);
        List<TdtNhomMon> subjectGroupList = subjectGroupRepository.findSubjectGroup(condition);
        return Optional.ofNullable(subjectGroupList).isPresent()
                ? subjectGroupList.stream()
                .map(this::buildSubjectGroup)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private SubjectGroupCondition buildSubjectGroupCondition(String groupIds) {
        return SubjectGroupCondition.builder()
                .groupIds(Optional.ofNullable(groupIds).isPresent()
                        ? Arrays.stream(groupIds.split(","))
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                .build();
    }

    private SubjectGroup buildSubjectGroup(TdtNhomMon subjectGroup) {
        return SubjectGroup.builder()
                .maNhom(subjectGroup.getMaNhom())
                .tenNhom(subjectGroup.getTenNhom())
                .build();
    }

    public String createSubject(SubjectCreate subjectCreate, String createBy) {
        String subjectCode = subjectCreate.getMaMon();
        if (Optional.ofNullable(subjectCode).isPresent()) {
            if (subjectManageService.checkExistSubject(subjectCode)) {
                throw new BusinessException("40001", "The subject already exists in the system! (" + subjectCode + ")");
            }

            return subjectRepository.create(this.buildTdtMonHocForCreate(subjectCreate, createBy)) > 0
                    ? SUCCESSFUL
                    : FAIL;
        }
        throw new BusinessException("40002", "Subject code is null!");
    }

    private TdtMonHoc buildTdtMonHocForCreate(SubjectCreate subjectCreate, String createBy) {
        return TdtMonHoc.builder()
                .maMon(subjectCreate.getMaMon())
                .maNhom(subjectCreate.getMaNhom())
                .tenMon(subjectCreate.getTenMon())
                .soTiet(subjectCreate.getSoTiet())
                .maLoai(subjectCreate.getMaLoai())
                .createdAt(DateUtil.getTimeNow())
                .createdBy(createBy)
                .build();
    }

    public String deleteSubject(SubjectDeleteRequest request) {
        SubjectCondition condition = this.buildSubjectConditionForDelete(request);
        if (!ArrayUtil.isNotNullAndNotEmptyList(condition.getSubjectIds())) {
            throw new BusinessException("40001", "The list of deleted Subject is empty!");
        }
        return subjectRepository.delete(condition) > 0
                ? SUCCESSFUL
                : FAIL;
    }

    private SubjectCondition buildSubjectConditionForDelete(SubjectDeleteRequest request) {
        return SubjectCondition.builder()
                .subjectIds(Optional.ofNullable(request.getSubjectIds()).isPresent()
                        ? Arrays.stream(request.getSubjectIds().split(",")).collect(Collectors.toList())
                        : Collections.emptyList())
                .deleteBy(request.getDeleteBy())
                .build();
    }

    public String updateSubject(String subjectId, SubjectUpdate subject, String updateBy) {
        if (Optional.ofNullable(subjectId).isPresent()) {
            if (!subjectManageService.checkExistSubject(subjectId)) {
                throw new BusinessException("40001", "Not found Subject with ID: " + subjectId);
            }

            return subjectRepository.update(this.buildTdtMonHocForUpdate(subject, updateBy), subjectId) > 0
                    ? SUCCESSFUL
                    : FAIL;
        }
        return FAIL;
    }

    private TdtMonHoc buildTdtMonHocForUpdate(SubjectUpdate subject, String updateBy) {
        return TdtMonHoc.builder()
                .tenMon(subject.getTenMon())
                .soTiet(subject.getSoTiet())
                .updateBy(updateBy)
                .updatedAt(DateUtil.getTimeNow())
                .build();
    }
}
