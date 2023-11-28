package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtNamHocExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtNamHocMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtNamHoc;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
@ComponentScan
public class SchoolYearRepositoryImp implements SchoolYearRepository {
    private final TdtNamHocMapper schoolYearMapper;

    @Override
    public Long countSchoolYear() {
        TdtNamHocExample example = new TdtNamHocExample();
        TdtNamHocExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return schoolYearMapper.countByExample(example);
    }

    @Override
    public List<TdtNamHoc> findSchoolYear() {
        TdtNamHocExample example = new TdtNamHocExample();
        TdtNamHocExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        example.setOrderByClause("NAM_HOC DESC");
        return schoolYearMapper.selectByExample(example);
    }
}
