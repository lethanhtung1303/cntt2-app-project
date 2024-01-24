package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtCertificate;
import com.tdtu.webproject.exception.BusinessException;
import com.tdtu.webproject.mybatis.condition.CertificateCondition;
import com.tdtu.webproject.repository.CertificateRepository;
import com.tdtu.webproject.utils.*;
import generater.openapi.model.CertificateCreate;
import generater.openapi.model.CertificateDeleteRequest;
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
public class CertificateService {
    private final LecturerManageService lecturerManageService;
    private final CertificateRepository certificateRepository;

    public List<TdtCertificate> findByLecturerId(String lecturerIds) {
        CertificateCondition condition = this.buildCertificateCondition(lecturerIds);
        return certificateRepository.findCertificate(condition);
    }

    private CertificateCondition buildCertificateCondition(String lecturerIds) {
        return CertificateCondition.builder()
                .lecturerIds(Optional.ofNullable(lecturerIds).isPresent()
                        ? Arrays.stream(lecturerIds.split(","))
                        .map(lecturerId -> NumberUtil.toBigDecimal(lecturerId).orElse(null))
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                // Add conditions if needed
                .build();
    }

    public String deleteCertificate(CertificateDeleteRequest request) {
        CertificateCondition condition = this.buildCertificateConditionForDelete(request);
        if (!ArrayUtil.isNotNullAndNotEmptyList(condition.getCertificateIds())) {
            throw new BusinessException("40001",
                    MessageProperties.getInstance().getProperty(DELETED_CERTIFICATE_EMPTY)
            );
        }
        return certificateRepository.delete(condition) > 0
                ? SUCCESSFUL
                : FAIL;
    }

    private CertificateCondition buildCertificateConditionForDelete(CertificateDeleteRequest request) {
        return CertificateCondition.builder()
                .certificateIds(Optional.ofNullable(request.getCertificateId()).isPresent()
                        ? Arrays.stream(request.getCertificateId().split(","))
                        .map(id -> NumberUtil.toBigDecimal(id).orElse(null))
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                .updateBy(request.getDeleteBy())
                .build();
    }

    public String createCertificate(BigDecimal lecturerId, CertificateCreate certificateCreate, String createBy) {
        if (Optional.ofNullable(lecturerId).isPresent()) {
            if (lecturerManageService.checkNotExistLecturer(lecturerId)) {
                throw new BusinessException("40001",
                        MessageProperties.getInstance().getProperty(LECTURER_NOT_FOUND, StringUtil.convertBigDecimalToString(lecturerId))
                );
            }

            return certificateRepository.create(this.buildTdtChungChiForCreate(lecturerId, certificateCreate, createBy)) > 0
                    ? SUCCESSFUL
                    : FAIL;
        }
        throw new BusinessException("40005",
                MessageProperties.getInstance().getProperty(LECTURER_ID_NULL)
        );
    }

    private TdtCertificate buildTdtChungChiForCreate(BigDecimal lecturerId, CertificateCreate certificateCreate, String createBy) {
        return TdtCertificate.builder()
                .lecturerId(lecturerId)
                .grade(certificateCreate.getDiem())
                .certificateType(certificateCreate.getMaLoai())
                .createdAt(DateUtil.getTimeNow())
                .createdBy(createBy)
                .build();
    }
}
