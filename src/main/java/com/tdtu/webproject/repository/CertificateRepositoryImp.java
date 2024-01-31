package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtCertificateExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtCertificateMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtCertificate;
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
    private final TdtCertificateMapper certificateMapper;

    @Override
    public Long countCertificate(CertificateCondition condition) {
        TdtCertificateExample example = new TdtCertificateExample();
        TdtCertificateExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getLecturerIds())) {
            criteria.andLecturerIdIn(condition.getLecturerIds());
        }
        criteria.andIsActiveEqualTo(true);
        return certificateMapper.countByExample(example);
    }

    @Override
    public List<TdtCertificate> findCertificate(CertificateCondition condition) {
        TdtCertificateExample example = new TdtCertificateExample();
        TdtCertificateExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getLecturerIds())) {
            criteria.andLecturerIdIn(condition.getLecturerIds());
        }
        criteria.andIsActiveEqualTo(true);
        return certificateMapper.selectByExample(example);
    }

    @Override
    public int delete(CertificateCondition condition) {
        TdtCertificateExample example = new TdtCertificateExample();
        TdtCertificateExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getCertificateIds())) {
            criteria.andIdIn(condition.getCertificateIds());
        }
        criteria.andIsActiveEqualTo(true);

        TdtCertificate record = TdtCertificate.builder()
                .isActive(false)
                .updatedAt(DateUtil.getTimeNow())
                .updateBy(condition.getUpdateBy())
                .build();
        return certificateMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int create(TdtCertificate record) {
        return certificateMapper.insertSelective(record);
    }
}
