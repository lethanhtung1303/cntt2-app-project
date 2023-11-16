package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtMonHocExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtMonHocMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtMonHoc;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
@ComponentScan
public class SubjectRepositoryImp implements SubjectRepository {
    private final TdtMonHocMapper subjectMapper;

    @Override
    public List<TdtMonHoc> getAllSubject() {
        TdtMonHocExample example = new TdtMonHocExample();
        TdtMonHocExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return subjectMapper.selectByExample(example);
    }
}
