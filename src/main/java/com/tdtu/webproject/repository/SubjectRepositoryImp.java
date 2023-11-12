package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtMonHocExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtMonHocMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtMonHoc;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@ComponentScan
public class SubjectRepositoryImp implements SubjectRepository {
    private final TdtMonHocMapper subjectMapper;

    @Override
    public List<TdtMonHoc> findSubjectByMaMon(String maMon) {
        TdtMonHocExample example = new TdtMonHocExample();
        TdtMonHocExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(maMon).ifPresent(criteria::andMaMonEqualTo);
        criteria.andIsActiveEqualTo(true);
        return subjectMapper.selectByExample(example);
    }
}
