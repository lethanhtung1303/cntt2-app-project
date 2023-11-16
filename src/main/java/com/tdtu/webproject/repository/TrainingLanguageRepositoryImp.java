package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtNgonNguDaoTaoExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtNgonNguDaoTaoMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtNgonNguDaoTao;
import com.tdtu.webproject.model.condition.TrainingLanguageCondition;
import com.tdtu.webproject.utils.ArrayUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
@ComponentScan
public class TrainingLanguageRepositoryImp implements TrainingLanguageRepository {
    private final TdtNgonNguDaoTaoMapper trainingLanguageMapper;

    @Override
    public Long countAllTrainingLanguage(TrainingLanguageCondition condition) {
        TdtNgonNguDaoTaoExample example = new TdtNgonNguDaoTaoExample();
        TdtNgonNguDaoTaoExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getTrainingProcessIds())) {
            criteria.andQuaTrinhDaoTaoIdIn(condition.getTrainingProcessIds());
        }
        criteria.andIsActiveEqualTo(true);
        return trainingLanguageMapper.countByExample(example);
    }

    @Override
    public List<TdtNgonNguDaoTao> findTrainingLanguage(TrainingLanguageCondition condition) {
        TdtNgonNguDaoTaoExample example = new TdtNgonNguDaoTaoExample();
        TdtNgonNguDaoTaoExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getTrainingProcessIds())) {
            criteria.andQuaTrinhDaoTaoIdIn(condition.getTrainingProcessIds());
        }
        criteria.andIsActiveEqualTo(true);
        return trainingLanguageMapper.selectByExample(example);
    }

    @Override
    public Long countAllTrainingLanguage() {
        TdtNgonNguDaoTaoExample example = new TdtNgonNguDaoTaoExample();
        TdtNgonNguDaoTaoExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return trainingLanguageMapper.countByExample(example);
    }

    @Override
    public List<TdtNgonNguDaoTao> getAllTrainingLanguage() {
        TdtNgonNguDaoTaoExample example = new TdtNgonNguDaoTaoExample();
        TdtNgonNguDaoTaoExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return trainingLanguageMapper.selectByExample(example);
    }
}
