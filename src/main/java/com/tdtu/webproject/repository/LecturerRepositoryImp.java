package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtLecturerExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtLecturerMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtLecturer;
import com.tdtu.webproject.mybatis.condition.LecturerCondition;
import com.tdtu.webproject.mybatis.condition.LecturerTeachingHistoryCondition;
import com.tdtu.webproject.mybatis.condition.NormsLectureHoursCondition;
import com.tdtu.webproject.mybatis.mapper.LecturerSupportMapper;
import com.tdtu.webproject.mybatis.result.*;
import com.tdtu.webproject.utils.ArrayUtil;
import com.tdtu.webproject.utils.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@ComponentScan
public class LecturerRepositoryImp implements LecturerRepository {
    private final TdtLecturerMapper lecturerMapper;
    private final LecturerSupportMapper lecturerSupportMapper;

    @Override
    public Long countLecturer(LecturerCondition condition) {
        TdtLecturerExample example = new TdtLecturerExample();
        TdtLecturerExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getLecturerIds())) {
            criteria.andIdIn(condition.getLecturerIds());
        }
        criteria.andDeletedFlagEqualTo(false);
        return lecturerMapper.countByExample(example);
    }

    @Override
    public List<TdtLecturer> findLecturer(LecturerCondition condition) {
        TdtLecturerExample example = new TdtLecturerExample();
        TdtLecturerExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getLecturerIds())) {
            criteria.andIdIn(condition.getLecturerIds());
        }
        Optional.ofNullable(condition.getEmailTdtu()).ifPresent(criteria::andEmailTdtuEqualTo);
        criteria.andDeletedFlagEqualTo(false);
        return lecturerMapper.selectByExample(example);
    }

    @Override
    public int update(TdtLecturer record, BigDecimal lecturerId) {
        TdtLecturerExample example = new TdtLecturerExample();
        TdtLecturerExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(lecturerId).ifPresent(criteria::andIdEqualTo);
        criteria.andDeletedFlagEqualTo(false);
        return lecturerMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int delete(LecturerCondition condition) {
        TdtLecturerExample example = new TdtLecturerExample();
        TdtLecturerExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getLecturerIds())) {
            criteria.andIdIn(condition.getLecturerIds());
        }
        criteria.andDeletedFlagEqualTo(false);

        TdtLecturer record = TdtLecturer.builder()
                .deletedFlag(true)
                .deletedAt(DateUtil.getTimeNow())
                .deletedBy(condition.getDeleteBy())
                .build();
        return lecturerMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int create(TdtLecturer record) {
        return lecturerMapper.insertSelective(record);
    }

    @Override
    public List<LecturerTeachingResult> getLecturerTeaching() {
        return lecturerSupportMapper.getLecturerTeaching();
    }

    @Override
    public List<LecturerTrainingProcessResult> getLecturerTrainingProcess() {
        return lecturerSupportMapper.getLecturerTrainingProcess();
    }

    @Override
    public List<LecturerTrainingLanguageResult> getLecturerTrainingLanguage() {
        return lecturerSupportMapper.getLecturerTrainingLanguage();
    }

    @Override
    public List<LecturerCertificateResult> getLecturerCertificate() {
        return lecturerSupportMapper.getLecturerCertificate();
    }

    @Override
    public List<LecturerSatisfactionScoreResult> getLecturerSatisfactionScore(BigDecimal semester) {
        return lecturerSupportMapper.getLecturerSatisfactionScore(semester);
    }

    @Override
    public List<NormsLectureHoursResult> getNormsLectureHours(NormsLectureHoursCondition condition) {
        return lecturerSupportMapper.getNormsLectureHours(condition);
    }

    @Override
    public List<LecturerTeachingHistoryResult> getLecturerTeachingHistory(LecturerTeachingHistoryCondition condition) {
        return lecturerSupportMapper.getLecturerTeachingHistory(condition);
    }
}
