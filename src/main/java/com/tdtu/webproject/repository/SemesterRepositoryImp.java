package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtHocKyExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtHocKyMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtHocKy;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
@ComponentScan
public class SemesterRepositoryImp implements SemesterRepository {
    private final TdtHocKyMapper semesterMapper;

    @Override
    public Long countSemester() {
        TdtHocKyExample example = new TdtHocKyExample();
        TdtHocKyExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return semesterMapper.countByExample(example);
    }

    @Override
    public List<TdtHocKy> findSemester() {
        TdtHocKyExample example = new TdtHocKyExample();
        TdtHocKyExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        example.setOrderByClause("HOC_KY DESC");
        return semesterMapper.selectByExample(example);
    }
}
