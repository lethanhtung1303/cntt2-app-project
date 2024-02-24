package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtSemesterExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtSemesterMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtSemester;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
@ComponentScan
public class SemesterRepositoryImp implements SemesterRepository {
    private final TdtSemesterMapper semesterMapper;

    @Override
    public Long countSemester() {
        TdtSemesterExample example = new TdtSemesterExample();
        TdtSemesterExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return semesterMapper.countByExample(example);
    }

    @Override
    public List<TdtSemester> findSemester() {
        TdtSemesterExample example = new TdtSemesterExample();
        TdtSemesterExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        example.setOrderByClause("SEMESTER DESC");
        return semesterMapper.selectByExample(example);
    }
}
