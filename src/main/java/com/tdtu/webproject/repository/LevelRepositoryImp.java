package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtDegreeExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtDegreeMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtDegree;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
@ComponentScan
public class LevelRepositoryImp implements LevelRepository {
    private final TdtDegreeMapper levelMapper;

    @Override
    public Long countAllLevel() {
        TdtDegreeExample example = new TdtDegreeExample();
        TdtDegreeExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return levelMapper.countByExample(example);
    }

    @Override
    public List<TdtDegree> getAllLevel() {
        TdtDegreeExample example = new TdtDegreeExample();
        TdtDegreeExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return levelMapper.selectByExample(example);
    }
}
