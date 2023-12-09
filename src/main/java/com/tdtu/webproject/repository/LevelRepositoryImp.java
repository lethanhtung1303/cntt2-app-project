package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtTrinhDoExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtTrinhDoMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtTrinhDo;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
@ComponentScan
public class LevelRepositoryImp implements LevelRepository {
    private final TdtTrinhDoMapper levelMapper;

    @Override
    public Long countAllLevel() {
        TdtTrinhDoExample example = new TdtTrinhDoExample();
        TdtTrinhDoExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return levelMapper.countByExample(example);
    }

    @Override
    public List<TdtTrinhDo> getAllLevel() {
        TdtTrinhDoExample example = new TdtTrinhDoExample();
        TdtTrinhDoExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return levelMapper.selectByExample(example);
    }
}
