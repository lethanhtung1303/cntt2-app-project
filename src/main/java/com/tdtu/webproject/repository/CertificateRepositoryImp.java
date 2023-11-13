package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtChungChiExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtChungChiMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtChungChi;
import com.tdtu.webproject.model.condition.CertificateCondition;
import com.tdtu.webproject.utils.ArrayUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
@ComponentScan
public class CertificateRepositoryImp implements CertificateRepository {
    private final TdtChungChiMapper certificateMapper;

    @Override
    public Long countCertificate(CertificateCondition condition) {
        TdtChungChiExample example = new TdtChungChiExample();
        TdtChungChiExample.Criteria criteria = example.createCriteria();
        if (!ArrayUtil.isNotNullOrEmptyList(condition.getLecturerIds())) {
            criteria.andGiangVienIdIn(condition.getLecturerIds());
        }
        criteria.andIsActiveEqualTo(true);
        return certificateMapper.countByExample(example);
    }

    @Override
    public List<TdtChungChi> findCertificate(CertificateCondition condition) {
        TdtChungChiExample example = new TdtChungChiExample();
        TdtChungChiExample.Criteria criteria = example.createCriteria();
        if (!ArrayUtil.isNotNullOrEmptyList(condition.getLecturerIds())) {
            criteria.andGiangVienIdIn(condition.getLecturerIds());
        }
        criteria.andIsActiveEqualTo(true);
        return certificateMapper.selectByExample(example);
    }
}
