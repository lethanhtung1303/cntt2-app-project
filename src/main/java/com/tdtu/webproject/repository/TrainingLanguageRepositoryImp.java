package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtTrainingLanguageExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtTrainingLanguageMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtTrainingLanguage;
import com.tdtu.webproject.mybatis.condition.TrainingLanguageCondition;
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
public class TrainingLanguageRepositoryImp implements TrainingLanguageRepository {
    private final TdtTrainingLanguageMapper trainingLanguageMapper;

    @Override
    public Long countAllTrainingLanguage(TrainingLanguageCondition condition) {
        TdtTrainingLanguageExample example = new TdtTrainingLanguageExample();
        TdtTrainingLanguageExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getTrainingProcessIds())) {
            criteria.andTrainingProcessIdIn(condition.getTrainingProcessIds());
        }
        criteria.andIsActiveEqualTo(true);
        return trainingLanguageMapper.countByExample(example);
    }

    @Override
    public List<TdtTrainingLanguage> findTrainingLanguage(TrainingLanguageCondition condition) {
        TdtTrainingLanguageExample example = new TdtTrainingLanguageExample();
        TdtTrainingLanguageExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getTrainingProcessIds())) {
            criteria.andTrainingProcessIdIn(condition.getTrainingProcessIds());
        }
        criteria.andIsActiveEqualTo(true);
        return trainingLanguageMapper.selectByExample(example);
    }

    @Override
    public Long countAllTrainingLanguage() {
        TdtTrainingLanguageExample example = new TdtTrainingLanguageExample();
        TdtTrainingLanguageExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return trainingLanguageMapper.countByExample(example);
    }

    @Override
    public List<TdtTrainingLanguage> getAllTrainingLanguage() {
        TdtTrainingLanguageExample example = new TdtTrainingLanguageExample();
        TdtTrainingLanguageExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return trainingLanguageMapper.selectByExample(example);
    }

    @Override
    public int create(TdtTrainingLanguage record) {
        return trainingLanguageMapper.insertSelective(record);
    }

    @Override
    public int deleteByTrainingId(BigDecimal trainingId) {
        TdtTrainingLanguageExample example = new TdtTrainingLanguageExample();
        TdtTrainingLanguageExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(trainingId).ifPresent(criteria::andTrainingProcessIdEqualTo);
        TdtTrainingLanguage record = TdtTrainingLanguage.builder()
                .isActive(false)
                .build();
        return trainingLanguageMapper.updateByExampleSelective(record, example);
    }
}
