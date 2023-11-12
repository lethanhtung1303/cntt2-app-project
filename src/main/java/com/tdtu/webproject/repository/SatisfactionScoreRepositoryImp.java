package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtDiemHaiLongExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtDiemHaiLongMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtDiemHaiLong;
import com.tdtu.webproject.model.condition.SatisfactionScoreCondition;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@ComponentScan
public class SatisfactionScoreRepositoryImp implements SatisfactionScoreRepository {
    private final TdtDiemHaiLongMapper satisfactionScoreMapper;

    @Override
    public Long countSatisfactionScore(SatisfactionScoreCondition condition) {
        TdtDiemHaiLongExample example = new TdtDiemHaiLongExample();
        TdtDiemHaiLongExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(condition.getLecturerIds()).ifPresent(criteria::andIdIn);
        criteria.andIsActiveEqualTo(true);
        return satisfactionScoreMapper.countByExample(example);
    }

    @Override
    public List<TdtDiemHaiLong> findSatisfactionScore(SatisfactionScoreCondition condition) {
        TdtDiemHaiLongExample example = new TdtDiemHaiLongExample();
        TdtDiemHaiLongExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(condition.getLecturerIds()).ifPresent(criteria::andIdIn);
        criteria.andIsActiveEqualTo(true);
        return satisfactionScoreMapper.selectByExample(example);
    }
}
