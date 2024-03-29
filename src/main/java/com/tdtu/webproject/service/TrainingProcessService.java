package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtTrainingLanguage;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtTrainingProcess;
import com.tdtu.webproject.exception.BusinessException;
import com.tdtu.webproject.mybatis.condition.TrainingProcessCondition;
import com.tdtu.webproject.repository.TrainingLanguageRepository;
import com.tdtu.webproject.repository.TrainingProcessRepository;
import com.tdtu.webproject.utils.*;
import generater.openapi.model.TrainingProcessCreate;
import generater.openapi.model.TrainingProcessDeleteRequest;
import generater.openapi.model.TrainingProcessUpdate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tdtu.webproject.constant.Const.*;

@Service
@AllArgsConstructor
public class TrainingProcessService {

    private final LecturerManageService lecturerManageService;
    private final TrainingProcessManageService trainingProcessManageService;
    private final TrainingProcessRepository trainingProcessRepository;
    private final TrainingLanguageRepository trainingLanguageRepository;

    public List<TdtTrainingProcess> findByLecturerId(String lecturerIds) {
        TrainingProcessCondition condition = this.buildTrainingProcessCondition(lecturerIds);
        return trainingProcessRepository.findTrainingProcess(condition);
    }

    private TrainingProcessCondition buildTrainingProcessCondition(String lecturerIds) {
        return TrainingProcessCondition.builder()
                .lecturerIds(Optional.ofNullable(lecturerIds).isPresent()
                        ? Arrays.stream(lecturerIds.split(","))
                        .map(lecturerId -> NumberUtil.toBigDecimal(lecturerId).orElse(null))
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                // Add conditions if needed
                .build();
    }

    public String createTrainingProcess(BigDecimal lecturerId, TrainingProcessCreate createRequest, String createBy) {
        if (Optional.ofNullable(lecturerId).isPresent()) {
            if (lecturerManageService.checkNotExistLecturer(lecturerId)) {
                throw new BusinessException("40001",
                        MessageProperties.getInstance().getProperty(LECTURER_NOT_FOUND, StringUtil.convertBigDecimalToString(lecturerId))
                );
            }

            List<TdtTrainingProcess> createList =
                    trainingProcessRepository.create(this.buildTdtTrainingProcessForCreate(lecturerId, createRequest, createBy));

            if (!ArrayUtil.isNotNullAndNotEmptyList(createList)) {
                throw new BusinessException("40002",
                        MessageProperties.getInstance().getProperty(TRAINING_PROCESS_CREATE_FAIL)
                );
            }

            TdtTrainingProcess create = createList.stream().findFirst().orElse(null);
            if (Optional.ofNullable(create).isEmpty()) {
                throw new BusinessException("40002",
                        MessageProperties.getInstance().getProperty(TRAINING_PROCESS_CREATE_FAIL)
                );
            }

            List<TdtTrainingLanguage> trainingLanguageList = this.buildTdtTrainingLanguage(create.getId(),
                    createRequest.getLanguageIds(),
                    createBy);
            if (!ArrayUtil.isNotNullAndNotEmptyList(trainingLanguageList)) {
                throw new BusinessException("40003",
                        MessageProperties.getInstance().getProperty(TRAINING_LANGUAGE_EMPTY)
                );
            }

            int totalResult = trainingLanguageList.stream().mapToInt(trainingLanguageRepository::create).sum();
            if (totalResult == trainingLanguageList.size()) {
                return SUCCESSFUL;
            } else {
                throw new BusinessException("40004",
                        MessageProperties.getInstance().getProperty(TRAINING_LANGUAGE_CREATE_FAIL)
                );
            }
        }
        throw new BusinessException("40005",
                MessageProperties.getInstance().getProperty(LECTURER_ID_NULL)
        );
    }

    private List<TdtTrainingLanguage> buildTdtTrainingLanguage(BigDecimal trainingProcessId, String languageIds, String user) {
        List<BigDecimal> languageIdList = Arrays.stream(languageIds.split(","))
                .map(languageId -> NumberUtil.toBigDecimal(languageId).orElse(null))
                .toList();

        return ArrayUtil.isNotNullAndNotEmptyList(languageIdList)
                ? languageIdList.stream()
                .map(languageId -> TdtTrainingLanguage.builder()
                        .languageId(languageId)
                        .trainingProcessId(trainingProcessId)
                        .createdBy(user)
                        .createdAt(DateUtil.getTimeNow())
                        .build())
                .toList()
                : Collections.emptyList();
    }

    private TdtTrainingProcess buildTdtTrainingProcessForCreate(BigDecimal lecturerId, TrainingProcessCreate trainingProcess, String createBy) {
        return TdtTrainingProcess.builder()
                .lecturerId(lecturerId)
                .qualificationId(trainingProcess.getLevel())
                .university(trainingProcess.getTruong())
                .major(trainingProcess.getNganh())
                .graduationYear(trainingProcess.getNamTotNghiep())
                .thesisTitle(trainingProcess.getDeTaiTotNghiep())
                .instructor(trainingProcess.getNguoiHuongDan())
                .graduationTypeId(trainingProcess.getLoaiTotNghiep())
                .createdBy(createBy)
                .createdAt(DateUtil.getTimeNow())
                .build();
    }

    public String updateTrainingProcess(BigDecimal processId, TrainingProcessUpdate updateRequest, String updateBy) {
        if (Optional.ofNullable(processId).isPresent()) {
            if (!trainingProcessManageService.checkExistTrainingProcess(processId)) {
                throw new BusinessException("40001",
                        MessageProperties.getInstance().getProperty(TRAINING_PROCESS_NOT_FOUND, StringUtil.convertBigDecimalToString(processId))
                );
            }

            int updated = trainingProcessRepository.update(this.buildTdtQuaTrinhDaoTaoForUpdate(updateRequest, updateBy), processId);

            if (updated <= 0) {
                throw new BusinessException("40002",
                        MessageProperties.getInstance().getProperty(TRAINING_PROCESS_CREATE_FAIL)
                );
            }

            List<TdtTrainingLanguage> trainingLanguageList = this.buildTdtTrainingLanguage(processId, updateRequest.getLanguageIds(),
                    updateBy);
            if (!ArrayUtil.isNotNullAndNotEmptyList(trainingLanguageList)) {
                throw new BusinessException("40003",
                        MessageProperties.getInstance().getProperty(TRAINING_LANGUAGE_EMPTY)
                );
            }
            trainingLanguageRepository.deleteByTrainingId(processId);
            int totalResult = trainingLanguageList.stream().mapToInt(trainingLanguageRepository::create).sum();
            if (totalResult == trainingLanguageList.size()) {
                return SUCCESSFUL;
            } else {
                throw new BusinessException("40004",
                        MessageProperties.getInstance().getProperty(TRAINING_LANGUAGE_CREATE_FAIL)
                );
            }
        }
        return FAIL;
    }

    private TdtTrainingProcess buildTdtQuaTrinhDaoTaoForUpdate(TrainingProcessUpdate trainingProcess, String updateBy) {
        return TdtTrainingProcess.builder()
                .qualificationId(trainingProcess.getLevel())
                .university(trainingProcess.getTruong())
                .major(trainingProcess.getNganh())
                .graduationYear(trainingProcess.getNamTotNghiep())
                .thesisTitle(trainingProcess.getDeTaiTotNghiep())
                .instructor(trainingProcess.getNguoiHuongDan())
                .graduationTypeId(trainingProcess.getLoaiTotNghiep())
                .updatedAt(DateUtil.getTimeNow())
                .updateBy(updateBy)
                .build();
    }

    public String deleteTrainingProcess(TrainingProcessDeleteRequest request) {
        TrainingProcessCondition condition = this.buildTrainingProcessConditionForDelete(request);
        if (!ArrayUtil.isNotNullAndNotEmptyList(condition.getProcessIds())) {
            throw new BusinessException("40001",
                    MessageProperties.getInstance().getProperty(DELETED_TRAINING_PROCESS_EMPTY)
            );
        }
        return trainingProcessRepository.delete(condition) > 0
                ? SUCCESSFUL
                : FAIL;
    }

    private TrainingProcessCondition buildTrainingProcessConditionForDelete(TrainingProcessDeleteRequest request) {
        return TrainingProcessCondition.builder()
                .processIds(Optional.ofNullable(request.getTrainingProcessId()).isPresent()
                        ? Arrays.stream(request.getTrainingProcessId().split(","))
                        .map(lecturerId -> NumberUtil.toBigDecimal(lecturerId).orElse(null))
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                .updateBy(request.getDeleteBy())
                .build();
    }
}
