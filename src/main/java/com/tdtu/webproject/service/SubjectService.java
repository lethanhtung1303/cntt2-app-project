package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtHeDaoTao;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtSubject;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtSubjectGroup;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtSubjectType;
import com.tdtu.webproject.exception.BusinessException;
import com.tdtu.webproject.mybatis.condition.SubjectCondition;
import com.tdtu.webproject.mybatis.condition.SubjectGroupCondition;
import com.tdtu.webproject.mybatis.condition.SubjectTrainingSysCondition;
import com.tdtu.webproject.repository.SubjectGroupRepository;
import com.tdtu.webproject.repository.SubjectRepository;
import com.tdtu.webproject.repository.SubjectTrainingSysRepository;
import com.tdtu.webproject.repository.SubjectTypeRepository;
import com.tdtu.webproject.utils.ArrayUtil;
import com.tdtu.webproject.utils.DateUtil;
import com.tdtu.webproject.utils.MessageProperties;
import com.tdtu.webproject.utils.NumberUtil;
import generater.openapi.model.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tdtu.webproject.constant.Const.*;

@Service
@AllArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectGroupRepository subjectGroupRepository;
    private final SubjectTrainingSysRepository subjectTrainingSysRepository;
    private final SubjectTypeRepository subjectTypeRepository;
    private final SubjectManageService subjectManageService;

    public List<TdtSubject> getAllSubject() {
        return subjectRepository.getAllSubject();
    }

    public Long count(String subjectIds) {
        SubjectCondition condition = this.buildSubjectCondition(subjectIds);
        return subjectRepository.countSubject(condition);
    }

    public List<Subject> find(String subjectIds) {
        SubjectCondition condition = this.buildSubjectCondition(subjectIds);
        List<TdtSubject> subjectList = subjectRepository.findSubject(condition);
        return Optional.ofNullable(subjectList).isPresent()
                ? subjectList.stream()
                .map(this::buildSubject)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private Subject buildSubject(TdtSubject subject) {
        TdtSubjectGroup subjectGroup = subjectGroupRepository.getSubjectGroupById(subject.getSubjectId());
        TdtSubjectType subjectType = subjectTypeRepository.getSubjectTypeById(subject.getTypeId());
        return Subject.builder()
                .maMon(subject.getSubjectId())
                .phanLoai(subjectType.getType())
                .subjectGroup(SubjectGroup.builder()
                        .maNhom(subjectGroup.getGroupId())
                        .tenNhom(subjectGroup.getGroupName())
                        .build())
                .tenMon(subject.getSubjectName())
                .soTiet(subject.getTotalShift())
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
        List<TdtSubjectGroup> subjectGroupList = subjectGroupRepository.findSubjectGroup(condition);
        return Optional.ofNullable(subjectGroupList).isPresent()
                ? subjectGroupList.stream()
                .map(this::buildSubjectGroup)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    public Long countSubjectTrainingSys(String trainingSysIds) {
        SubjectTrainingSysCondition condition = this.buildSubjectTrainingSysCondition(trainingSysIds);
        return subjectTrainingSysRepository.countSubjectTrainingSys(condition);
    }

    public List<TrainingSys> findSubjectTrainingSys(String trainingSysIds) {
        SubjectTrainingSysCondition condition = this.buildSubjectTrainingSysCondition(trainingSysIds);
        List<TdtHeDaoTao> subjectGroupList = subjectTrainingSysRepository.findSubjectTrainingSys(condition);
        return Optional.ofNullable(subjectGroupList).isPresent()
                ? subjectGroupList.stream()
                .map(this::buildTrainingSys)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private SubjectTrainingSysCondition buildSubjectTrainingSysCondition(String trainingSysIds) {
        return SubjectTrainingSysCondition.builder()
                .trainingSysIds(Optional.ofNullable(trainingSysIds).isPresent()
                        ? Arrays.stream(trainingSysIds.split(","))
                        .map(lecturerId -> NumberUtil.toBigDecimal(lecturerId).orElse(null))
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                .build();
    }

    private TrainingSys buildTrainingSys(TdtHeDaoTao trainingSys) {
        return TrainingSys.builder()
                .maHe(trainingSys.getMaHe())
                .phanHe(trainingSys.getPhanHe())
                .build();
    }

    private SubjectGroupCondition buildSubjectGroupCondition(String groupIds) {
        return SubjectGroupCondition.builder()
                .groupIds(Optional.ofNullable(groupIds).isPresent()
                        ? Arrays.stream(groupIds.split(","))
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                .build();
    }

    private SubjectGroup buildSubjectGroup(TdtSubjectGroup subjectGroup) {
        return SubjectGroup.builder()
                .maNhom(subjectGroup.getGroupId())
                .tenNhom(subjectGroup.getGroupName())
                .build();
    }

    public String createSubject(SubjectCreate subjectCreate, String createBy) {
        String subjectCode = subjectCreate.getMaMon();
        if (Optional.ofNullable(subjectCode).isPresent()) {
            if (subjectManageService.checkExistSubject(subjectCode)) {
                throw new BusinessException("40001",
                        MessageProperties.getInstance().getProperty(SUBJECT_ALREADY, subjectCode)
                );
            }

            return subjectRepository.create(this.buildTdtMonHocForCreate(subjectCreate, createBy)) > 0
                    ? SUCCESSFUL
                    : FAIL;
        }
        throw new BusinessException("40002",
                MessageProperties.getInstance().getProperty(SUBJECT_CODE_NULL)
        );
    }

    private TdtSubject buildTdtMonHocForCreate(SubjectCreate subjectCreate, String createBy) {
        return TdtSubject.builder()
                .subjectId(subjectCreate.getMaMon())
                .groupId(subjectCreate.getMaNhom())
                .subjectName(subjectCreate.getTenMon())
                .totalShift(subjectCreate.getSoTiet())
                .typeId(subjectCreate.getMaLoai())
                .createdAt(DateUtil.getTimeNow())
                .createdBy(createBy)
                .build();
    }

    public String deleteSubject(SubjectDeleteRequest request) {
        SubjectCondition condition = this.buildSubjectConditionForDelete(request);
        if (!ArrayUtil.isNotNullAndNotEmptyList(condition.getSubjectIds())) {
            throw new BusinessException("40001",
                    MessageProperties.getInstance().getProperty(DELETED_SUBJECT_EMPTY)
            );
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
                throw new BusinessException("40001",
                        MessageProperties.getInstance().getProperty(SUBJECT_NOT_FOUND, subjectId)
                );
            }

            return subjectRepository.update(this.buildTdtMonHocForUpdate(subject, updateBy), subjectId) > 0
                    ? SUCCESSFUL
                    : FAIL;
        }
        return FAIL;
    }

    private TdtSubject buildTdtMonHocForUpdate(SubjectUpdate subject, String updateBy) {
        return TdtSubject.builder()
                .subjectName(subject.getTenMon())
                .totalShift(subject.getSoTiet())
                .updateBy(updateBy)
                .updatedAt(DateUtil.getTimeNow())
                .build();
    }
}
