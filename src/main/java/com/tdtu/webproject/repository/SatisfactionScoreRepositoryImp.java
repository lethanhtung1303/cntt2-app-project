package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtDiemHaiLongExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtDiemHaiLongMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtDiemHaiLong;
import com.tdtu.webproject.mybatis.condition.SatisfactionScoreCondition;
import com.tdtu.webproject.utils.ArrayUtil;
import com.tdtu.webproject.utils.DateUtil;
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

    @Override
    public int delete(SatisfactionScoreCondition condition) {
        TdtDiemHaiLongExample example = new TdtDiemHaiLongExample();
        TdtDiemHaiLongExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getSatisfactionScoreIds())) {
            criteria.andIdIn(condition.getSatisfactionScoreIds());
        }
        criteria.andIsActiveEqualTo(true);

        TdtDiemHaiLong record = TdtDiemHaiLong.builder()
                .isActive(false)
                .updatedAt(DateUtil.getTimeNow())
                .updateBy(condition.getUpdateBy())
                .build();
        return satisfactionScoreMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int create(TdtDiemHaiLong record, String createBy) {
        TdtDiemHaiLongExample example = new TdtDiemHaiLongExample();
        TdtDiemHaiLongExample.Criteria criteria = example.createCriteria();
        criteria.andGiangVienIdEqualTo(record.getGiangVienId());
        criteria.andMaMonEqualTo(record.getMaMon());
        criteria.andHocKyEqualTo(record.getHocKy());
        criteria.andIsActiveEqualTo(true);
        if (satisfactionScoreMapper.countByExample(example) > 0) {
            record.setUpdateBy(createBy);
            record.setUpdatedAt(DateUtil.getTimeNow());
            return satisfactionScoreMapper.updateByExampleSelective(record, example);
        }
        record.setCreatedBy(createBy);
        record.setCreatedAt(DateUtil.getTimeNow());
        return satisfactionScoreMapper.insertSelective(record);
    }
}
