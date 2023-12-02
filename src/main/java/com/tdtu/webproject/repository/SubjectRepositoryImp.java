package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtMonHocExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtMonHocMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtMonHoc;
import com.tdtu.webproject.model.condition.SubjectCondition;
import com.tdtu.webproject.utils.ArrayUtil;
import com.tdtu.webproject.utils.DateUtil;
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
        example.setOrderByClause("CREATED_AT DESC");
        return subjectMapper.selectByExample(example);
    }

    @Override
    public List<TdtMonHoc> getAllSubject() {
        TdtMonHocExample example = new TdtMonHocExample();
        TdtMonHocExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return subjectMapper.selectByExample(example);
    }

    @Override
    public int create(TdtMonHoc record) {
        return subjectMapper.insertSelective(record);
    }

    @Override
    public int delete(SubjectCondition condition) {
        TdtMonHocExample example = new TdtMonHocExample();
        TdtMonHocExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getSubjectIds())) {
            criteria.andMaMonIn(condition.getSubjectIds());
        }
        criteria.andIsActiveEqualTo(true);

        TdtMonHoc record = TdtMonHoc.builder()
                .isActive(false)
                .updatedAt(DateUtil.getTimeNow())
                .updateBy(condition.getDeleteBy())
                .build();
        return subjectMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int update(TdtMonHoc record, String subjectId) {
        TdtMonHocExample example = new TdtMonHocExample();
        TdtMonHocExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(subjectId).ifPresent(criteria::andMaMonEqualTo);
        criteria.andIsActiveEqualTo(true);
        return subjectMapper.updateByExampleSelective(record, example);
    }
}
