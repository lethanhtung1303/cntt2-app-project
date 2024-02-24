package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtTeachingLogExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtTeachingLogMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtTeachingLog;
import com.tdtu.webproject.mybatis.condition.TeachingDiaryCondition;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@ComponentScan
public class TeachingDiaryRepositoryImp implements TeachingDiaryRepository {
    private final TdtTeachingLogMapper teachingLogMapper;

    @Override
    public Long countTeachingDiary(TeachingDiaryCondition condition) {
        TdtTeachingLogExample example = new TdtTeachingLogExample();
        TdtTeachingLogExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(condition.getHistoryId()).ifPresent(criteria::andTeachingHistoryIdEqualTo);
        criteria.andIsActiveEqualTo(true);
        return teachingLogMapper.countByExample(example);
    }

    @Override
    public List<TdtTeachingLog> findTeachingDiary(TeachingDiaryCondition condition) {
        TdtTeachingLogExample example = new TdtTeachingLogExample();
        TdtTeachingLogExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(condition.getHistoryId()).ifPresent(criteria::andTeachingHistoryIdEqualTo);
        criteria.andIsActiveEqualTo(true);
        return teachingLogMapper.selectByExample(example);
    }

    @Override
    public int create(TdtTeachingLog record) {
        return teachingLogMapper.insertSelective(record);
    }

    @Override
    public int update(TdtTeachingLog record, BigDecimal historyId) {
        TdtTeachingLogExample example = new TdtTeachingLogExample();
        TdtTeachingLogExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(historyId).ifPresent(criteria::andTeachingHistoryIdEqualTo);
        criteria.andIsActiveEqualTo(true);
        return teachingLogMapper.updateByExampleSelective(record, example);
    }
}
