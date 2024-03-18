package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtCertificateType;

import java.math.BigDecimal;
import java.util.List;

public interface CertificateTypeRepository {
    Long countAllCertificateType();

    List<TdtCertificateType> getAllCertificateType();

    TdtCertificateType getCertificateTypeById(BigDecimal typeId);
}
