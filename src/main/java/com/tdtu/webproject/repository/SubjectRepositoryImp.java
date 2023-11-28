package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtMonHocExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtMonHocMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtMonHoc;
import com.tdtu.webproject.model.condition.SubjectCondition;
import com.tdtu.webproject.utils.ArrayUtil;
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
    public Long countSubject(SubjectCondition condition) {
        TdtMonHocExample example = new TdtMonHocExample();
        TdtMonHocExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getSubjectIds())) {
            criteria.andMaMonIn(condition.getSubjectIds());
        }
        criteria.andIsActiveEqualTo(true);
        return subjectMapper.countByExample(example);
    }

    @Override
    public List<TdtMonHoc> findSubject(SubjectCondition condition) {
        TdtMonHocExample example = new TdtMonHocExample();
        TdtMonHocExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getSubjectIds())) {
            criteria.andMaMonIn(condition.getSubjectIds());
        }
        criteria.andIsActiveEqualTo(true);
        return subjectMapper.selectByExample(example);
    }

    @Override
    public List<TdtMonHoc> getAllSubject() {
        TdtMonHocExample example = new TdtMonHocExample();
        TdtMonHocExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return subjectMapper.selectByExample(example);
    }
}
