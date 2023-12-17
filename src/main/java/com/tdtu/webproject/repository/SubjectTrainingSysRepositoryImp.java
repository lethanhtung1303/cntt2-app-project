package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtHeDaoTaoExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtHeDaoTaoMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtHeDaoTao;
import com.tdtu.webproject.model.condition.SubjectTrainingSysCondition;
import com.tdtu.webproject.utils.ArrayUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@ComponentScan
public class SubjectTrainingSysRepositoryImp implements SubjectTrainingSysRepository {
    private final TdtHeDaoTaoMapper subjectTrainingSysMapper;

    @Override
    public Long countSubjectTrainingSys(SubjectTrainingSysCondition condition) {
        TdtHeDaoTaoExample example = new TdtHeDaoTaoExample();
        TdtHeDaoTaoExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getTrainingSysIds())) {
            criteria.andMaHeIn(condition.getTrainingSysIds());
        }
        criteria.andIsActiveEqualTo(true);
        return subjectTrainingSysMapper.countByExample(example);
    }

    @Override
    public List<TdtHeDaoTao> findSubjectTrainingSys(SubjectTrainingSysCondition condition) {
        TdtHeDaoTaoExample example = new TdtHeDaoTaoExample();
        TdtHeDaoTaoExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getTrainingSysIds())) {
            criteria.andMaHeIn(condition.getTrainingSysIds());
        }
        criteria.andIsActiveEqualTo(true);
        return subjectTrainingSysMapper.selectByExample(example);
    }

    @Override
    public TdtHeDaoTao getSubjectTrainingSysById(BigDecimal trainingSysId) {
        TdtHeDaoTaoExample example = new TdtHeDaoTaoExample();
        TdtHeDaoTaoExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(trainingSysId).ifPresent(criteria::andMaHeEqualTo);
        criteria.andIsActiveEqualTo(true);
        return subjectTrainingSysMapper.selectByExample(example).stream()
                .findFirst()
                .orElse(null);
    }
}
