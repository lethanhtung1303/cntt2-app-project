package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtSatisfactoryScoreExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtSatisfactoryScoreMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtSatisfactoryScore;
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
    private final TdtSatisfactoryScoreMapper satisfactionScoreMapper;

    @Override
    public Long countSatisfactionScore(SatisfactionScoreCondition condition) {
        TdtSatisfactoryScoreExample example = new TdtSatisfactoryScoreExample();
        TdtSatisfactoryScoreExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getLecturerIds())) {
            criteria.andLecturerIdIn(condition.getLecturerIds());
        }
        criteria.andIsActiveEqualTo(true);
        return satisfactionScoreMapper.countByExample(example);
    }

    @Override
    public List<TdtSatisfactoryScore> findSatisfactionScore(SatisfactionScoreCondition condition) {
        TdtSatisfactoryScoreExample example = new TdtSatisfactoryScoreExample();
        TdtSatisfactoryScoreExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getLecturerIds())) {
            criteria.andLecturerIdIn(condition.getLecturerIds());
        }
        criteria.andIsActiveEqualTo(true);
        return satisfactionScoreMapper.selectByExample(example);
    }

    @Override
    public int delete(SatisfactionScoreCondition condition) {
        TdtSatisfactoryScoreExample example = new TdtSatisfactoryScoreExample();
        TdtSatisfactoryScoreExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getSatisfactionScoreIds())) {
            criteria.andIdIn(condition.getSatisfactionScoreIds());
        }
        criteria.andIsActiveEqualTo(true);

        TdtSatisfactoryScore record = TdtSatisfactoryScore.builder()
                .isActive(false)
                .updatedAt(DateUtil.getTimeNow())
                .updateBy(condition.getUpdateBy())
                .build();
        return satisfactionScoreMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int create(TdtSatisfactoryScore record, String createBy) {
        TdtSatisfactoryScoreExample example = new TdtSatisfactoryScoreExample();
        TdtSatisfactoryScoreExample.Criteria criteria = example.createCriteria();
        criteria.andLecturerIdEqualTo(record.getLecturerId());
        criteria.andSubjectIdEqualTo(record.getSubjectId());
        criteria.andSemesterEqualTo(record.getSemester());
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
