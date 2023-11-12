package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtQuaTrinhDaoTaoExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtQuaTrinhDaoTaoMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtQuaTrinhDaoTao;
import com.tdtu.webproject.model.condition.TrainingProcessCondition;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@ComponentScan
public class TrainingProcessRepositoryImp implements TrainingProcessRepository {
    private final TdtQuaTrinhDaoTaoMapper trainingProcessMapper;

    @Override
    public Long countTrainingProcess(TrainingProcessCondition condition) {
        TdtQuaTrinhDaoTaoExample example = new TdtQuaTrinhDaoTaoExample();
        TdtQuaTrinhDaoTaoExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(condition.getLecturerIds()).ifPresent(criteria::andIdIn);
        criteria.andIsActiveEqualTo(true);
        return trainingProcessMapper.countByExample(example);
    }

    @Override
    public List<TdtQuaTrinhDaoTao> findTrainingProcess(TrainingProcessCondition condition) {
        TdtQuaTrinhDaoTaoExample example = new TdtQuaTrinhDaoTaoExample();
        TdtQuaTrinhDaoTaoExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(condition.getLecturerIds()).ifPresent(criteria::andIdIn);
        criteria.andIsActiveEqualTo(true);
        return trainingProcessMapper.selectByExample(example);
    }
}
