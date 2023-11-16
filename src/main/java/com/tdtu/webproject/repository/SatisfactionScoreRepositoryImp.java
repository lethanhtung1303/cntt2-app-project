package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtDiemHaiLongExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtDiemHaiLongMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtDiemHaiLong;
import com.tdtu.webproject.model.condition.SatisfactionScoreCondition;
import com.tdtu.webproject.utils.ArrayUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
@ComponentScan
public class SatisfactionScoreRepositoryImp implements SatisfactionScoreRepository {
    private final TdtDiemHaiLongMapper satisfactionScoreMapper;

    @Override
    public Long countSatisfactionScore(SatisfactionScoreCondition condition) {
        TdtDiemHaiLongExample example = new TdtDiemHaiLongExample();
        TdtDiemHaiLongExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getLecturerIds())) {
            criteria.andGiangVienIdIn(condition.getLecturerIds());
        }
        criteria.andIsActiveEqualTo(true);
        return satisfactionScoreMapper.countByExample(example);
    }

    @Override
    public List<TdtDiemHaiLong> findSatisfactionScore(SatisfactionScoreCondition condition) {
        TdtDiemHaiLongExample example = new TdtDiemHaiLongExample();
        TdtDiemHaiLongExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getLecturerIds())) {
            criteria.andGiangVienIdIn(condition.getLecturerIds());
        }
        criteria.andIsActiveEqualTo(true);
        return satisfactionScoreMapper.selectByExample(example);
    }
}
