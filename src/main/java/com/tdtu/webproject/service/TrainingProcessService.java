package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtNgonNguDaoTao;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtQuaTrinhDaoTao;
import com.tdtu.webproject.constant.Const;
import com.tdtu.webproject.exception.BusinessException;
import com.tdtu.webproject.model.condition.TrainingProcessCondition;
import com.tdtu.webproject.repository.TrainingLanguageRepository;
import com.tdtu.webproject.repository.TrainingProcessRepository;
import com.tdtu.webproject.utils.ArrayUtil;
import com.tdtu.webproject.utils.DateUtil;
import com.tdtu.webproject.utils.NumberUtil;
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

@Service
@AllArgsConstructor
public class TrainingProcessService {

    private final LecturerManageService lecturerManageService;
    private final TrainingProcessManageService trainingProcessManageService;
    private final TrainingProcessRepository trainingProcessRepository;
    private final TrainingLanguageRepository trainingLanguageRepository;

    public List<TdtQuaTrinhDaoTao> findByLecturerId(String lecturerIds) {
        TrainingProcessCondition condition = this.buildTrainingProcessCondition(lecturerIds);
        return trainingProcessRepository.findTrainingProcess(condition);
    }

    private TrainingProcessCondition buildTrainingProcessCondition(String lecturerIds) {
        return TrainingProcessCondition.builder()
                .lecturerIds(Optional.ofNullable(lecturerIds).isPresent()
                        ? Arrays.stream(lecturerIds.split(","))
                        .map(lecturerId -> NumberUtil.toBigDeimal(lecturerId).orElse(null))
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                // Add conditions if needed
                .build();
    }

    public String createTrainingProcess(BigDecimal lecturerId, TrainingProcessCreate createRequest, String createBy) {
        if (Optional.ofNullable(lecturerId).isPresent()) {
            if (!lecturerManageService.checkExistLecturer(lecturerId)) {
                throw new BusinessException("40001", "Not found Lecturer with ID: " + lecturerId);
            }

            List<TdtQuaTrinhDaoTao> createList =
                    trainingProcessRepository.create(this.buildTdtQuaTrinhDaoTaoForCreate(lecturerId, createRequest, createBy));

            if (!ArrayUtil.isNotNullAndNotEmptyList(createList)) {
                throw new BusinessException("40002", "Training Process create FAIL!");
            }

            TdtQuaTrinhDaoTao create = createList.stream().findFirst().orElse(null);
            if (Optional.ofNullable(create).isEmpty()) {
                throw new BusinessException("40002", "Training Process create FAIL!");
            }

            List<TdtNgonNguDaoTao> trainingLanguageList = this.buildTdtNgonNguDaoTao(create.getId(), createRequest.getLanguageIds(), createBy);
            if (!ArrayUtil.isNotNullAndNotEmptyList(trainingLanguageList)) {
                throw new BusinessException("40003", "Training Language is empty list!");
            }

            int totalResult = trainingLanguageList.stream().mapToInt(trainingLanguageRepository::create).sum();
            if (totalResult == trainingLanguageList.size()) {
                return Const.SUCCESSFUL;
            } else {
                throw new BusinessException("40004", "Training Language create FAIL!");
            }
        }
        throw new BusinessException("40005", "Lecturer Id is null!");
    }

    private List<TdtNgonNguDaoTao> buildTdtNgonNguDaoTao(BigDecimal trainingProcessId, String languageIds, String user) {
        List<BigDecimal> languageIdList = Arrays.stream(languageIds.split(","))
                .map(languageId -> NumberUtil.toBigDeimal(languageId).orElse(null))
                .toList();

        return ArrayUtil.isNotNullAndNotEmptyList(languageIdList)
                ? languageIdList.stream()
                .map(languageId -> TdtNgonNguDaoTao.builder()
                        .ngonNguId(languageId)
                        .quaTrinhDaoTaoId(trainingProcessId)
                        .createdBy(user)
                        .createdAt(DateUtil.getTimeNow())
                        .build())
                .toList()
                : Collections.emptyList();
    }

    private TdtQuaTrinhDaoTao buildTdtQuaTrinhDaoTaoForCreate(BigDecimal lecturerId, TrainingProcessCreate trainingProcess, String createBy) {
        return TdtQuaTrinhDaoTao.builder()
                .giangVienId(lecturerId)
                .trinhDoId(trainingProcess.getLevel())
                .truong(trainingProcess.getTruong())
                .nganh(trainingProcess.getNganh())
                .namTotNghiep(trainingProcess.getNamTotNghiep())
                .deTaiTotNghiep(trainingProcess.getDeTaiTotNghiep())
                .nguoiHuongDan(trainingProcess.getNguoiHuongDan())
                .loaiTotNghiepId(trainingProcess.getLoaiTotNghiep())
                .createdBy(createBy)
                .createdAt(DateUtil.getTimeNow())
                .build();
    }

    public String updateTrainingProcess(BigDecimal processId, TrainingProcessUpdate updateRequest, String updateBy) {
        if (Optional.ofNullable(processId).isPresent()) {
            if (!trainingProcessManageService.checkExistTrainingProcess(processId)) {
                throw new BusinessException("40001", "Not found Training Process with ID: " + processId);
            }

            int updated = trainingProcessRepository.update(this.buildTdtQuaTrinhDaoTaoForUpdate(updateRequest, updateBy), processId);

            if (updated <= 0) {
                throw new BusinessException("40002", "Training Process update FAIL!");
            }

            List<TdtNgonNguDaoTao> trainingLanguageList = this.buildTdtNgonNguDaoTao(processId, updateRequest.getLanguageIds(), updateBy);
            if (!ArrayUtil.isNotNullAndNotEmptyList(trainingLanguageList)) {
                throw new BusinessException("40003", "Training Language is empty list!");
            }
            trainingLanguageRepository.deleteByTrainingId(processId);
            int totalResult = trainingLanguageList.stream().mapToInt(trainingLanguageRepository::create).sum();
            if (totalResult == trainingLanguageList.size()) {
                return Const.SUCCESSFUL;
            } else {
                throw new BusinessException("40004", "Training Language create FAIL!");
            }
        }
        return Const.FAIL;
    }

    private TdtQuaTrinhDaoTao buildTdtQuaTrinhDaoTaoForUpdate(TrainingProcessUpdate trainingProcess, String updateBy) {
        return TdtQuaTrinhDaoTao.builder()
                .trinhDoId(trainingProcess.getLevel())
                .truong(trainingProcess.getTruong())
                .nganh(trainingProcess.getNganh())
                .namTotNghiep(trainingProcess.getNamTotNghiep())
                .deTaiTotNghiep(trainingProcess.getDeTaiTotNghiep())
                .nguoiHuongDan(trainingProcess.getNguoiHuongDan())
                .loaiTotNghiepId(trainingProcess.getLoaiTotNghiep())
                .updatedAt(DateUtil.getTimeNow())
                .updateBy(updateBy)
                .build();
    }

    public String deleteTrainingProcess(TrainingProcessDeleteRequest request) {
        TrainingProcessCondition condition = this.buildTrainingProcessConditionForDelete(request);
        if (!ArrayUtil.isNotNullAndNotEmptyList(condition.getProcessIds())) {
            throw new BusinessException("40001", "The list of deleted Training Process is empty!");
        }
        return trainingProcessRepository.delete(condition) > 0
                ? Const.SUCCESSFUL
                : Const.FAIL;
    }

    private TrainingProcessCondition buildTrainingProcessConditionForDelete(TrainingProcessDeleteRequest request) {
        return TrainingProcessCondition.builder()
                .processIds(Optional.ofNullable(request.getTrainingProcessId()).isPresent()
                        ? Arrays.stream(request.getTrainingProcessId().split(","))
                        .map(lecturerId -> NumberUtil.toBigDeimal(lecturerId).orElse(null))
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                .updateBy(request.getDeleteBy())
                .build();
    }
}
