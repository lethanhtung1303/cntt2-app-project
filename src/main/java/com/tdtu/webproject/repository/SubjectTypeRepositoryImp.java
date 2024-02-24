package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtSubjectTypeExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtSubjectTypeMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtSubjectType;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@ComponentScan
public class SubjectTypeRepositoryImp implements SubjectTypeRepository {
    private final TdtSubjectTypeMapper subjectTypeMapper;

    @Override
    public Long countAllSubjectType() {
        TdtSubjectTypeExample example = new TdtSubjectTypeExample();
        TdtSubjectTypeExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return subjectTypeMapper.countByExample(example);
    }

    @Override
    public List<TdtSubjectType> getAllSubjectType() {
        TdtSubjectTypeExample example = new TdtSubjectTypeExample();
        TdtSubjectTypeExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return subjectTypeMapper.selectByExample(example);
    }

    @Override
    public TdtSubjectType getSubjectTypeById(BigDecimal typeId) {
        TdtSubjectTypeExample example = new TdtSubjectTypeExample();
        TdtSubjectTypeExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(typeId).ifPresent(criteria::andTypeIdEqualTo);
        criteria.andIsActiveEqualTo(true);
        return subjectTypeMapper.selectByExample(example).stream()
                .findFirst()
                .orElse(null);
    }
}
