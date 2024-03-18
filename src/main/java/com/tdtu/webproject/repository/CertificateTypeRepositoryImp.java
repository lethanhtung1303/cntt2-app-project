package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtCertificateTypeExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtCertificateTypeMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtCertificateType;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@ComponentScan
public class CertificateTypeRepositoryImp implements CertificateTypeRepository {
    private final TdtCertificateTypeMapper certificateTypeMapper;

    @Override
    public Long countAllCertificateType() {
        TdtCertificateTypeExample example = new TdtCertificateTypeExample();
        TdtCertificateTypeExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return certificateTypeMapper.countByExample(example);
    }

    @Override
    public List<TdtCertificateType> getAllCertificateType() {
        TdtCertificateTypeExample example = new TdtCertificateTypeExample();
        TdtCertificateTypeExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return certificateTypeMapper.selectByExample(example);
    }

    @Override
    public TdtCertificateType getCertificateTypeById(BigDecimal TypeId) {
        TdtCertificateTypeExample example = new TdtCertificateTypeExample();
        TdtCertificateTypeExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(TypeId).ifPresent(criteria::andTypeIdEqualTo);
        criteria.andIsActiveEqualTo(true);
        return certificateTypeMapper.selectByExample(example).stream()
                .findFirst()
                .orElse(null);
    }
}
