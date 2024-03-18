package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtTrainingProcessExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtTrainingProcessMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtTrainingProcess;
import com.tdtu.webproject.mybatis.condition.TrainingProcessCondition;
import com.tdtu.webproject.utils.ArrayUtil;
import com.tdtu.webproject.utils.DateUtil;
import com.tdtu.webproject.utils.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@ComponentScan
public class TrainingProcessRepositoryImp implements TrainingProcessRepository {
    private final TdtTrainingProcessMapper trainingProcessMapper;

    @Override
    public Long countTrainingProcess(TrainingProcessCondition condition) {
        TdtTrainingProcessExample example = new TdtTrainingProcessExample();
        TdtTrainingProcessExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getLecturerIds())) {
            criteria.andLecturerIdIn(condition.getLecturerIds());
        }
        criteria.andIsActiveEqualTo(true);
        return trainingProcessMapper.countByExample(example);
    }

    @Override
    public List<TdtTrainingProcess> findTrainingProcess(TrainingProcessCondition condition) {
        TdtTrainingProcessExample example = new TdtTrainingProcessExample();
        TdtTrainingProcessExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getLecturerIds())) {
            criteria.andLecturerIdIn(condition.getLecturerIds());
        }
        criteria.andIsActiveEqualTo(true);
        return trainingProcessMapper.selectByExample(example);
    }

    @Override
    public List<TdtTrainingProcess> create(TdtTrainingProcess record) {
        int insert = trainingProcessMapper.insertSelective(record);
        if (insert > 0) {
            TdtTrainingProcessExample example = new TdtTrainingProcessExample();
            TdtTrainingProcessExample.Criteria criteria = example.createCriteria();
            criteria.andLecturerIdEqualTo(record.getLecturerId());
            criteria.andQualificationIdEqualTo(record.getQualificationId());
            if (StringUtil.isNotNullOrEmptyString(record.getUniversity())) {
                criteria.andUniversityEqualTo(record.getUniversity());
            }
            if (StringUtil.isNotNullOrEmptyString(record.getMajor())) {
                criteria.andMajorEqualTo(record.getMajor());
            }
            criteria.andGraduationYearEqualTo(record.getGraduationYear());
            if (StringUtil.isNotNullOrEmptyString(record.getThesisTitle())) {
                criteria.andThesisTitleEqualTo(record.getThesisTitle());
            }
            if (StringUtil.isNotNullOrEmptyString(record.getInstructor())) {
                criteria.andInstructorEqualTo(record.getInstructor());
            }
            criteria.andGraduationTypeIdEqualTo(record.getGraduationTypeId());
            if (StringUtil.isNotNullOrEmptyString(record.getCreatedBy())) {
                criteria.andCreatedByEqualTo(record.getCreatedBy());
            }
            return trainingProcessMapper.selectByExample(example);
        }
        return Collections.emptyList();
    }

    @Override
    public int update(TdtTrainingProcess record, BigDecimal processId) {
        TdtTrainingProcessExample example = new TdtTrainingProcessExample();
        TdtTrainingProcessExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(processId).ifPresent(criteria::andIdEqualTo);
        return trainingProcessMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int delete(TrainingProcessCondition condition) {
        TdtTrainingProcessExample example = new TdtTrainingProcessExample();
        TdtTrainingProcessExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getProcessIds())) {
            criteria.andIdIn(condition.getProcessIds());
        }
        criteria.andIsActiveEqualTo(true);

        TdtTrainingProcess record = TdtTrainingProcess.builder()
                .isActive(false)
                .updatedAt(DateUtil.getTimeNow())
                .updateBy(condition.getUpdateBy())
                .build();
        return trainingProcessMapper.updateByExampleSelective(record, example);
    }
}
