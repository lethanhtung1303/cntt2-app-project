package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtNgonNguExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtNgonNguMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtNgonNgu;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
@ComponentScan
public class LanguageRepositoryImp implements LanguageRepository {
    private final TdtNgonNguMapper languageMapper;

    @Override
    public Long countAllLanguage() {
        TdtNgonNguExample example = new TdtNgonNguExample();
        TdtNgonNguExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return languageMapper.countByExample(example);
    }

    @Override
    public List<TdtNgonNgu> getAllLanguage() {
        TdtNgonNguExample example = new TdtNgonNguExample();
        TdtNgonNguExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return languageMapper.selectByExample(example);
    }
}
