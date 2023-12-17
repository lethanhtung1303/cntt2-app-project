package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtLoaiMonExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtLoaiMonMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtLoaiMon;
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
    private final TdtLoaiMonMapper subjectTypeMapper;

    @Override
    public Long countAllSubjectType() {
        TdtLoaiMonExample example = new TdtLoaiMonExample();
        TdtLoaiMonExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return subjectTypeMapper.countByExample(example);
    }

    @Override
    public List<TdtLoaiMon> getAllSubjectType() {
        TdtLoaiMonExample example = new TdtLoaiMonExample();
        TdtLoaiMonExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return subjectTypeMapper.selectByExample(example);
    }

    @Override
    public TdtLoaiMon getSubjectTypeById(BigDecimal typeId) {
        TdtLoaiMonExample example = new TdtLoaiMonExample();
        TdtLoaiMonExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(typeId).ifPresent(criteria::andMaLoaiEqualTo);
        criteria.andIsActiveEqualTo(true);
        return subjectTypeMapper.selectByExample(example).stream()
                .findFirst()
                .orElse(null);
    }
}
