package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtSubjectGroupExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtSubjectGroupMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtSubjectGroup;
import com.tdtu.webproject.mybatis.condition.SubjectGroupCondition;
import com.tdtu.webproject.utils.ArrayUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@ComponentScan
public class SubjectGroupRepositoryImp implements SubjectGroupRepository {
    private final TdtSubjectGroupMapper subjectGroupMapper;

    @Override
    public Long countAllSubjectGroup() {
        TdtSubjectGroupExample example = new TdtSubjectGroupExample();
        TdtSubjectGroupExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return subjectGroupMapper.countByExample(example);
    }

    @Override
    public Long countSubjectGroup(SubjectGroupCondition condition) {
        TdtSubjectGroupExample example = new TdtSubjectGroupExample();
        TdtSubjectGroupExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getGroupIds())) {
            criteria.andGroupIdIn(condition.getGroupIds());
        }
        criteria.andIsActiveEqualTo(true);
        return subjectGroupMapper.countByExample(example);
    }

    @Override
    public List<TdtSubjectGroup> getAllSubjectGroup() {
        TdtSubjectGroupExample example = new TdtSubjectGroupExample();
        TdtSubjectGroupExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return subjectGroupMapper.selectByExample(example);
    }

    @Override
    public TdtSubjectGroup getSubjectGroupById(String groupId) {
        TdtSubjectGroupExample example = new TdtSubjectGroupExample();
        TdtSubjectGroupExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(groupId).ifPresent(criteria::andGroupIdEqualTo);
        criteria.andIsActiveEqualTo(true);
        return subjectGroupMapper.selectByExample(example).stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<TdtSubjectGroup> findSubjectGroup(SubjectGroupCondition condition) {
        TdtSubjectGroupExample example = new TdtSubjectGroupExample();
        TdtSubjectGroupExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getGroupIds())) {
            criteria.andGroupIdIn(condition.getGroupIds());
        }
        criteria.andIsActiveEqualTo(true);
        return subjectGroupMapper.selectByExample(example);
    }
}
