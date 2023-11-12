package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtChungChiExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtChungChiMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtChungChi;
import com.tdtu.webproject.model.condition.CertificateCondition;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@ComponentScan
public class CertificateRepositoryImp implements CertificateRepository {
    private final TdtChungChiMapper certificateMapper;

    @Override
    public Long countCertificate(CertificateCondition condition) {
        TdtChungChiExample example = new TdtChungChiExample();
        TdtChungChiExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(condition.getLecturerIds()).ifPresent(criteria::andIdIn);
        criteria.andIsActiveEqualTo(true);
        return certificateMapper.countByExample(example);
    }

    @Override
    public List<TdtChungChi> findCertificate(CertificateCondition condition) {
        TdtChungChiExample example = new TdtChungChiExample();
        TdtChungChiExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(condition.getLecturerIds()).ifPresent(criteria::andIdIn);
        criteria.andIsActiveEqualTo(true);
        return certificateMapper.selectByExample(example);
    }
}
