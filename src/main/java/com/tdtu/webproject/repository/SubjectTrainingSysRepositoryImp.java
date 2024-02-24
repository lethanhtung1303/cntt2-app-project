package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtTrainingSystemExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtTrainingSystemMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtTrainingSystem;
import com.tdtu.webproject.mybatis.condition.SubjectTrainingSysCondition;
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
    private final TdtTrainingSystemMapper trainingSystemMapper;

    @Override
    public Long countSubjectTrainingSys(SubjectTrainingSysCondition condition) {
        TdtTrainingSystemExample example = new TdtTrainingSystemExample();
        TdtTrainingSystemExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getTrainingSysIds())) {
            criteria.andSystemIdIn(condition.getTrainingSysIds());
        }
        criteria.andIsActiveEqualTo(true);
        return trainingSystemMapper.countByExample(example);
    }

    @Override
    public List<TdtTrainingSystem> findSubjectTrainingSys(SubjectTrainingSysCondition condition) {
        TdtTrainingSystemExample example = new TdtTrainingSystemExample();
        TdtTrainingSystemExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getTrainingSysIds())) {
            criteria.andSystemIdIn(condition.getTrainingSysIds());
        }
        criteria.andIsActiveEqualTo(true);
        return trainingSystemMapper.selectByExample(example);
    }

    @Override
    public TdtTrainingSystem getSubjectTrainingSysById(BigDecimal trainingSysId) {
        TdtTrainingSystemExample example = new TdtTrainingSystemExample();
        TdtTrainingSystemExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(trainingSysId).ifPresent(criteria::andSystemIdEqualTo);
        criteria.andIsActiveEqualTo(true);
        return trainingSystemMapper.selectByExample(example).stream()
                .findFirst()
                .orElse(null);
    }
}
