package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtNhatKyGiangDayExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtNhatKyGiangDayMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtNhatKyGiangDay;
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
    private final TdtNhatKyGiangDayMapper teachingDiaryMapper;

    @Override
    public Long countTeachingDiary(TeachingDiaryCondition condition) {
        TdtNhatKyGiangDayExample example = new TdtNhatKyGiangDayExample();
        TdtNhatKyGiangDayExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(condition.getHistoryId()).ifPresent(criteria::andLichSuGiangDayIdEqualTo);
        criteria.andIsActiveEqualTo(true);
        return teachingDiaryMapper.countByExample(example);
    }

    @Override
    public List<TdtNhatKyGiangDay> findTeachingDiary(TeachingDiaryCondition condition) {
        TdtNhatKyGiangDayExample example = new TdtNhatKyGiangDayExample();
        TdtNhatKyGiangDayExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(condition.getHistoryId()).ifPresent(criteria::andLichSuGiangDayIdEqualTo);
        criteria.andIsActiveEqualTo(true);
        return teachingDiaryMapper.selectByExample(example);
    }

    @Override
    public int create(TdtNhatKyGiangDay record) {
        return teachingDiaryMapper.insertSelective(record);
    }

    @Override
    public int update(TdtNhatKyGiangDay record, BigDecimal historyId) {
        TdtNhatKyGiangDayExample example = new TdtNhatKyGiangDayExample();
        TdtNhatKyGiangDayExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(historyId).ifPresent(criteria::andLichSuGiangDayIdEqualTo);
        criteria.andIsActiveEqualTo(true);
        return teachingDiaryMapper.updateByExampleSelective(record, example);
    }
}
