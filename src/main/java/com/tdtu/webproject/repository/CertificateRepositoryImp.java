package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtChungChiExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtChungChiMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtChungChi;
import com.tdtu.webproject.mybatis.condition.CertificateCondition;
import com.tdtu.webproject.utils.ArrayUtil;
import com.tdtu.webproject.utils.DateUtil;
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
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getLecturerIds())) {
            criteria.andGiangVienIdIn(condition.getLecturerIds());
        }
        criteria.andIsActiveEqualTo(true);
        return certificateMapper.countByExample(example);
    }

    @Override
    public List<TdtChungChi> findCertificate(CertificateCondition condition) {
        TdtChungChiExample example = new TdtChungChiExample();
        TdtChungChiExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getLecturerIds())) {
            criteria.andGiangVienIdIn(condition.getLecturerIds());
        }
        criteria.andIsActiveEqualTo(true);
        return certificateMapper.selectByExample(example);
    }

    @Override
    public int delete(CertificateCondition condition) {
        TdtChungChiExample example = new TdtChungChiExample();
        TdtChungChiExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getCertificateIds())) {
            criteria.andIdIn(condition.getCertificateIds());
        }
        criteria.andIsActiveEqualTo(true);

        TdtChungChi record = TdtChungChi.builder()
                .isActive(false)
                .updatedAt(DateUtil.getTimeNow())
                .updateBy(condition.getUpdateBy())
                .build();
        return certificateMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int create(TdtChungChi record) {
        return certificateMapper.insertSelective(record);
    }
}
