package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtNhomMonExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtNhomMonMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtNhomMon;
import com.tdtu.webproject.model.condition.SubjectGroupCondition;
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
    private final TdtNhomMonMapper subjectGroupMapper;

    @Override
    public Long countAllSubjectGroup() {
        TdtNhomMonExample example = new TdtNhomMonExample();
        TdtNhomMonExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return subjectGroupMapper.countByExample(example);
    }

    @Override
    public Long countSubjectGroup(SubjectGroupCondition condition) {
        TdtNhomMonExample example = new TdtNhomMonExample();
        TdtNhomMonExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getGroupIds())) {
            criteria.andMaNhomIn(condition.getGroupIds());
        }
        criteria.andIsActiveEqualTo(true);
        return subjectGroupMapper.countByExample(example);
    }

    @Override
    public List<TdtNhomMon> getAllSubjectGroup() {
        TdtNhomMonExample example = new TdtNhomMonExample();
        TdtNhomMonExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return subjectGroupMapper.selectByExample(example);
    }

    @Override
    public TdtNhomMon getSubjectGroupById(String groupId) {
        TdtNhomMonExample example = new TdtNhomMonExample();
        TdtNhomMonExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(groupId).ifPresent(criteria::andMaNhomEqualTo);
        criteria.andIsActiveEqualTo(true);
        return subjectGroupMapper.selectByExample(example).stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<TdtNhomMon> findSubjectGroup(SubjectGroupCondition condition) {
        TdtNhomMonExample example = new TdtNhomMonExample();
        TdtNhomMonExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getGroupIds())) {
            criteria.andMaNhomIn(condition.getGroupIds());
        }
        criteria.andIsActiveEqualTo(true);
        return subjectGroupMapper.selectByExample(example);
    }
}
