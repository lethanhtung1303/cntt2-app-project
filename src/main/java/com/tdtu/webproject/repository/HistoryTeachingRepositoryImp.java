package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtLichSuGiangDayExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtLichSuGiangDayMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtLichSuGiangDay;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtNhatKyGiangDay;
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
    private final TdtLichSuGiangDayMapper historyTeachingMapper;
    private final TeachingDiaryRepository teachingDiaryRepository;

    @Override
    public Long countHistoryTeaching(HistoryTeachingCondition condition) {
        TdtLichSuGiangDayExample example = new TdtLichSuGiangDayExample();
        TdtLichSuGiangDayExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(condition.getLecturerId()).ifPresent(criteria::andGiangVienIdEqualTo);
        Optional.ofNullable(condition.getSemester()).ifPresent(criteria::andHocKyEqualTo);
        Optional.ofNullable(condition.getSubjectId()).ifPresent(criteria::andMaMonEqualTo);
        Optional.ofNullable(condition.getTrainingSysId()).ifPresent(criteria::andMaHeEqualTo);
        criteria.andIsActiveEqualTo(true);
        return historyTeachingMapper.countByExample(example);
    }

    @Override
    public List<TdtLichSuGiangDay> findHistoryTeaching(HistoryTeachingCondition condition) {
        TdtLichSuGiangDayExample example = new TdtLichSuGiangDayExample();
        TdtLichSuGiangDayExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(condition.getLecturerId()).ifPresent(criteria::andGiangVienIdEqualTo);
        Optional.ofNullable(condition.getSemester()).ifPresent(criteria::andHocKyEqualTo);
        Optional.ofNullable(condition.getSubjectId()).ifPresent(criteria::andMaMonEqualTo);
        Optional.ofNullable(condition.getTrainingSysId()).ifPresent(criteria::andMaHeEqualTo);
        criteria.andIsActiveEqualTo(true);
        return historyTeachingMapper.selectByExample(example);
    }

    @Override
    public int create(TdtLichSuGiangDay record) {
        int insert = historyTeachingMapper.insertSelective(record);
        if (insert == 0) {
            return 0;
        }

        HistoryTeachingCondition condition = HistoryTeachingCondition.builder()
                .lecturerId(record.getGiangVienId())
                .semester(record.getHocKy())
                .subjectId(record.getMaMon())
                .trainingSysId(record.getMaHe())
                .build();

        TdtLichSuGiangDayExample example = new TdtLichSuGiangDayExample();
        TdtLichSuGiangDayExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(condition.getLecturerId()).ifPresent(criteria::andGiangVienIdEqualTo);
        Optional.ofNullable(condition.getSemester()).ifPresent(criteria::andHocKyEqualTo);
        Optional.ofNullable(condition.getSubjectId()).ifPresent(criteria::andMaMonEqualTo);
        Optional.ofNullable(condition.getTrainingSysId()).ifPresent(criteria::andMaHeEqualTo);

        TdtLichSuGiangDay historyTeaching = historyTeachingMapper.selectByExample(example).stream().findFirst().orElse(null);
        if (Optional.ofNullable(historyTeaching).isEmpty()) {
            return 0;
        }

        TdtNhatKyGiangDay teachingDiary = TdtNhatKyGiangDay.builder()
                .lichSuGiangDayId(historyTeaching.getId())
                .createdBy(historyTeaching.getCreatedBy())
                .createdAt(historyTeaching.getCreatedAt())
                .build();

        teachingDiaryRepository.create(teachingDiary);
        return insert;
    }

    @Override
    public int delete(TeachingHistoryCondition condition) {
        TdtLichSuGiangDayExample example = new TdtLichSuGiangDayExample();
        TdtLichSuGiangDayExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getHistoryId())) {
            criteria.andIdIn(condition.getHistoryId());
        }
        criteria.andIsActiveEqualTo(true);

        TdtLichSuGiangDay record = TdtLichSuGiangDay.builder()
                .isActive(false)
                .updateBy(condition.getDeleteBy())
                .build();
        return historyTeachingMapper.updateByExampleSelective(record, example);
    }
}
