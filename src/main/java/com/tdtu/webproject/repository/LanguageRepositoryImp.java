package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtLanguageExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtLanguageMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtLanguage;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
@ComponentScan
public class LanguageRepositoryImp implements LanguageRepository {
    private final TdtLanguageMapper languageMapper;

    @Override
    public Long countAllLanguage() {
        TdtLanguageExample example = new TdtLanguageExample();
        TdtLanguageExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return languageMapper.countByExample(example);
    }

    @Override
    public List<TdtLanguage> getAllLanguage() {
        TdtLanguageExample example = new TdtLanguageExample();
        TdtLanguageExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return languageMapper.selectByExample(example);
    }
}
