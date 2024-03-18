package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtCertificateType;
import com.tdtu.webproject.repository.CertificateTypeRepository;
import generater.openapi.model.CertificateTypeDetailResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CertificateTypeService {
    private final CertificateTypeRepository certificateTypeRepository;

    public List<TdtCertificateType> getAllCertificateType() {
        return certificateTypeRepository.getAllCertificateType();
    }

    public TdtCertificateType getCertificateTypeById(BigDecimal TypeId) {
        return certificateTypeRepository.getCertificateTypeById(TypeId);
    }

    public List<CertificateTypeDetailResponse> findAll() {
        List<TdtCertificateType> certificateTypeList = certificateTypeRepository.getAllCertificateType();

        return Optional.ofNullable(certificateTypeList).isPresent()
                ? certificateTypeList.stream()
                .map(this::buildSchoolYearDetailResponse)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    public CertificateTypeDetailResponse buildSchoolYearDetailResponse(TdtCertificateType certificateType) {
        return CertificateTypeDetailResponse.builder()
                .value(certificateType.getTypeId())
                .label(certificateType.getType())
                .build();
    }
}
