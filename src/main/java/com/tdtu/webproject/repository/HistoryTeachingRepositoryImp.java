package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtLichSuGiangDayExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtLichSuGiangDayMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtLichSuGiangDay;
import com.tdtu.webproject.mybatis.condition.HistoryTeachingCondition;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@ComponentScan
public class HistoryTeachingRepositoryImp implements HistoryTeachingRepository {
    private final TdtLichSuGiangDayMapper historyTeachingMapper;

    @Override
    public Long countHistoryTeaching(HistoryTeachingCondition condition) {
        TdtLichSuGiangDayExample example = new TdtLichSuGiangDayExample();
        TdtLichSuGiangDayExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(condition.getLecturerId()).ifPresent(criteria::andGiangVienIdEqualTo);
        Optional.ofNullable(condition.getSemester()).ifPresent(criteria::andHocKyEqualTo);
        Optional.ofNullable(condition.getSubjectId()).ifPresent(criteria::andMaMonEqualTo);
        Optional.ofNullable(condition.getTrainingSysId()).ifPresent(criteria::andMaHeEqualTo);
        criteria.andIsActiveEqualTo(true);
        return historyTeachingMapper.countByExample(example);
    }

    @Override
    public List<TdtLichSuGiangDay> findHistoryTeaching(HistoryTeachingCondition condition) {
        TdtLichSuGiangDayExample example = new TdtLichSuGiangDayExample();
        TdtLichSuGiangDayExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(condition.getLecturerId()).ifPresent(criteria::andGiangVienIdEqualTo);
        Optional.ofNullable(condition.getSemester()).ifPresent(criteria::andHocKyEqualTo);
        Optional.ofNullable(condition.getSubjectId()).ifPresent(criteria::andMaMonEqualTo);
        Optional.ofNullable(condition.getTrainingSysId()).ifPresent(criteria::andMaHeEqualTo);
        criteria.andIsActiveEqualTo(true);
        return historyTeachingMapper.selectByExample(example);
    }

    @Override
    public int create(TdtLichSuGiangDay record) {
        return historyTeachingMapper.insertSelective(record);
    }
}
