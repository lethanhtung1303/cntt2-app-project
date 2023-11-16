package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtChungChi;
import com.tdtu.webproject.model.condition.CertificateCondition;

import java.util.List;

public interface CertificateRepository {
    Long countCertificate(CertificateCondition condition);

    List<TdtChungChi> findCertificate(CertificateCondition condition);
}