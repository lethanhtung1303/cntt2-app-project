package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtTeachingHistoryExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtTeachingHistoryMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtTeachingHistory;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtTeachingLog;
import com.tdtu.webproject.mybatis.condition.HistoryTeachingCondition;
import com.tdtu.webproject.mybatis.condition.TeachingHistoryCondition;
import com.tdtu.webproject.utils.ArrayUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@ComponentScan
public class HistoryTeachingRepositoryImp implements HistoryTeachingRepository {
    private final TdtTeachingHistoryMapper historyTeachingMapper;
    private final TeachingDiaryRepository teachingDiaryRepository;

    @Override
    public Long countHistoryTeaching(HistoryTeachingCondition condition) {
        TdtTeachingHistoryExample example = new TdtTeachingHistoryExample();
        TdtTeachingHistoryExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(condition.getLecturerId()).ifPresent(criteria::andLecturerIdEqualTo);
        Optional.ofNullable(condition.getSemester()).ifPresent(criteria::andSemesterIdEqualTo);
        Optional.ofNullable(condition.getSubjectId()).ifPresent(criteria::andSubjectIdEqualTo);
        Optional.ofNullable(condition.getTrainingSysId()).ifPresent(criteria::andSystemIdEqualTo);
        criteria.andIsActiveEqualTo(true);
        return historyTeachingMapper.countByExample(example);
    }

    @Override
    public List<TdtTeachingHistory> findHistoryTeaching(HistoryTeachingCondition condition) {
        TdtTeachingHistoryExample example = new TdtTeachingHistoryExample();
        TdtTeachingHistoryExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(condition.getLecturerId()).ifPresent(criteria::andLecturerIdEqualTo);
        Optional.ofNullable(condition.getSemester()).ifPresent(criteria::andSemesterIdEqualTo);
        Optional.ofNullable(condition.getSubjectId()).ifPresent(criteria::andSubjectIdEqualTo);
        Optional.ofNullable(condition.getTrainingSysId()).ifPresent(criteria::andSystemIdEqualTo);
        criteria.andIsActiveEqualTo(true);
        return historyTeachingMapper.selectByExample(example);
    }

    @Override
    public int create(TdtTeachingHistory record) {
        int insert = historyTeachingMapper.insertSelective(record);
        if (insert == 0) {
            return 0;
        }

        HistoryTeachingCondition condition = HistoryTeachingCondition.builder()
                .lecturerId(record.getLecturerId())
                .semester(record.getSemesterId())
                .subjectId(record.getSubjectId())
                .trainingSysId(record.getSystemId())
                .build();

        TdtTeachingHistoryExample example = new TdtTeachingHistoryExample();
        TdtTeachingHistoryExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(condition.getLecturerId()).ifPresent(criteria::andLecturerIdEqualTo);
        Optional.ofNullable(condition.getSemester()).ifPresent(criteria::andSemesterIdEqualTo);
        Optional.ofNullable(condition.getSubjectId()).ifPresent(criteria::andSubjectIdEqualTo);
        Optional.ofNullable(condition.getTrainingSysId()).ifPresent(criteria::andSystemIdEqualTo);

        TdtTeachingHistory historyTeaching = historyTeachingMapper.selectByExample(example).stream().findFirst().orElse(null);
        if (Optional.ofNullable(historyTeaching).isEmpty()) {
            return 0;
        }

        TdtTeachingLog teachingDiary = TdtTeachingLog.builder()
                .teachingHistoryId(historyTeaching.getId())
                .createdBy(historyTeaching.getCreatedBy())
                .createdAt(historyTeaching.getCreatedAt())
                .build();

        teachingDiaryRepository.create(teachingDiary);
        return insert;
    }

    @Override
    public int delete(TeachingHistoryCondition condition) {
        TdtTeachingHistoryExample example = new TdtTeachingHistoryExample();
        TdtTeachingHistoryExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getHistoryId())) {
            criteria.andIdIn(condition.getHistoryId());
        }
        criteria.andIsActiveEqualTo(true);

        TdtTeachingHistory record = TdtTeachingHistory.builder()
                .isActive(false)
                .updateBy(condition.getDeleteBy())
                .build();
        return historyTeachingMapper.updateByExampleSelective(record, example);
    }
}
