package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtGraduationTypeExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtGraduationTypeMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtGraduationType;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
@ComponentScan
public class GraduationTypeRepositoryImp implements GraduationTypeRepository {
    private final TdtGraduationTypeMapper graduationTypeMapper;

    @Override
    public Long countAllGraduationType() {
        TdtGraduationTypeExample example = new TdtGraduationTypeExample();
        TdtGraduationTypeExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return graduationTypeMapper.countByExample(example);
    }

    @Override
    public List<TdtGraduationType> getAllGraduationType() {
        TdtGraduationTypeExample example = new TdtGraduationTypeExample();
        TdtGraduationTypeExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return graduationTypeMapper.selectByExample(example);
    }
}
