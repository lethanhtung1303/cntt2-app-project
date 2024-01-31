package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtLecturerTypeExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtLecturerTypeMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtLecturerType;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
@ComponentScan
public class ClassificationLecturerRepositoryImp implements ClassificationLecturerRepository {
    private final TdtLecturerTypeMapper classificationLecturerMapper;

    @Override
    public Long countAllClassification() {
        TdtLecturerTypeExample example = new TdtLecturerTypeExample();
        return classificationLecturerMapper.countByExample(example);
    }

    @Override
    public List<TdtLecturerType> getAllClassification() {
        TdtLecturerTypeExample example = new TdtLecturerTypeExample();
        return classificationLecturerMapper.selectByExample(example);
    }
}
