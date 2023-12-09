package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtLoaiChungChi;

import java.math.BigDecimal;
import java.util.List;

public interface CertificateTypeRepository {
    Long countAllCertificateType();

    List<TdtLoaiChungChi> getAllCertificateType();

    TdtLoaiChungChi getCertificateTypeById(BigDecimal typeId);
}
