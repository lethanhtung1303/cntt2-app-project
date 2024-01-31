package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtSubjectExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtSubjectMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtSubject;
import com.tdtu.webproject.mybatis.condition.SubjectCondition;
import com.tdtu.webproject.utils.ArrayUtil;
import com.tdtu.webproject.utils.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@ComponentScan
public class SubjectRepositoryImp implements SubjectRepository {
    private final TdtSubjectMapper subjectMapper;

    @Override
    public Long countSubject(SubjectCondition condition) {
        TdtSubjectExample example = new TdtSubjectExample();
        TdtSubjectExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getSubjectIds())) {
            criteria.andSubjectIdIn(condition.getSubjectIds());
        }
        criteria.andIsActiveEqualTo(true);
        return subjectMapper.countByExample(example);
    }

    @Override
    public List<TdtSubject> findSubject(SubjectCondition condition) {
        TdtSubjectExample example = new TdtSubjectExample();
        TdtSubjectExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getSubjectIds())) {
            criteria.andSubjectIdIn(condition.getSubjectIds());
        }
        criteria.andIsActiveEqualTo(true);
        example.setOrderByClause("CREATED_AT DESC");
        return subjectMapper.selectByExample(example);
    }

    @Override
    public List<TdtSubject> getAllSubject() {
        TdtSubjectExample example = new TdtSubjectExample();
        TdtSubjectExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return subjectMapper.selectByExample(example);
    }

    @Override
    public int create(TdtSubject record) {
        return subjectMapper.insertSelective(record);
    }

    @Override
    public int delete(SubjectCondition condition) {
        TdtSubjectExample example = new TdtSubjectExample();
        TdtSubjectExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getSubjectIds())) {
            criteria.andSubjectIdIn(condition.getSubjectIds());
        }
        criteria.andIsActiveEqualTo(true);

        TdtSubject record = TdtSubject.builder()
                .isActive(false)
                .updatedAt(DateUtil.getTimeNow())
                .updateBy(condition.getDeleteBy())
                .build();
        return subjectMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int update(TdtSubject record, String subjectId) {
        TdtSubjectExample example = new TdtSubjectExample();
        TdtSubjectExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(subjectId).ifPresent(criteria::andSubjectIdEqualTo);
        criteria.andIsActiveEqualTo(true);
        return subjectMapper.updateByExampleSelective(record, example);
    }
}
