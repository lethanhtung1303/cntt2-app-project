package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtCertificate;
import com.tdtu.webproject.mybatis.condition.CertificateCondition;

import java.util.List;

public interface CertificateRepository {
    Long countCertificate(CertificateCondition condition);

    List<TdtCertificate> findCertificate(CertificateCondition condition);

    int delete(CertificateCondition condition);

    int create(TdtCertificate record);
}
