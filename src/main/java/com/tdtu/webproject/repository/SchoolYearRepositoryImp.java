package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtSchoolYearExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtSchoolYearMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtSchoolYear;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
@ComponentScan
public class SchoolYearRepositoryImp implements SchoolYearRepository {
    private final TdtSchoolYearMapper schoolYearMapper;

    @Override
    public Long countSchoolYear() {
        TdtSchoolYearExample example = new TdtSchoolYearExample();
        TdtSchoolYearExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return schoolYearMapper.countByExample(example);
    }

    @Override
    public List<TdtSchoolYear> findSchoolYear() {
        TdtSchoolYearExample example = new TdtSchoolYearExample();
        TdtSchoolYearExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        example.setOrderByClause("SCHOOL_YEAR DESC");
        return schoolYearMapper.selectByExample(example);
    }
}
