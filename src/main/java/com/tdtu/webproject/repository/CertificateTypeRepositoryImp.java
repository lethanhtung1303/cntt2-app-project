package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtLoaiChungChiExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtLoaiChungChiMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtLoaiChungChi;
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
    private final TdtLoaiChungChiMapper certificateTypeMapper;

    @Override
    public Long countAllCertificateType() {
        TdtLoaiChungChiExample example = new TdtLoaiChungChiExample();
        TdtLoaiChungChiExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return certificateTypeMapper.countByExample(example);
    }

    @Override
    public List<TdtLoaiChungChi> getAllCertificateType() {
        TdtLoaiChungChiExample example = new TdtLoaiChungChiExample();
        TdtLoaiChungChiExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return certificateTypeMapper.selectByExample(example);
    }

    @Override
    public TdtLoaiChungChi getCertificateTypeById(BigDecimal TypeId) {
        TdtLoaiChungChiExample example = new TdtLoaiChungChiExample();
        TdtLoaiChungChiExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(TypeId).ifPresent(criteria::andMaLoaiEqualTo);
        criteria.andIsActiveEqualTo(true);
        return certificateTypeMapper.selectByExample(example).stream()
                .findFirst()
                .orElse(null);
    }
}
