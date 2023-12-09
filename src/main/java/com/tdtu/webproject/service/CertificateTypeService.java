package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtLoaiChungChi;
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

    public List<TdtLoaiChungChi> getAllCertificateType() {
        return certificateTypeRepository.getAllCertificateType();
    }

    public TdtLoaiChungChi getCertificateTypeById(BigDecimal TypeId) {
        return certificateTypeRepository.getCertificateTypeById(TypeId);
    }

    public List<CertificateTypeDetailResponse> findAll() {
        List<TdtLoaiChungChi> certificateTypeList = certificateTypeRepository.getAllCertificateType();

        return Optional.ofNullable(certificateTypeList).isPresent()
                ? certificateTypeList.stream()
                .map(this::buildSchoolYearDetailResponse)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    public CertificateTypeDetailResponse buildSchoolYearDetailResponse(TdtLoaiChungChi certificateType) {
        return CertificateTypeDetailResponse.builder()
                .value(certificateType.getMaLoai())
                .label(certificateType.getPhanLoai())
                .build();
    }
}
