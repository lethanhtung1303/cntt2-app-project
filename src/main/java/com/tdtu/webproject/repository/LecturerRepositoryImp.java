package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtGiangVienExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtGiangVienMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtGiangVien;
import com.tdtu.webproject.model.condition.LecturerCondition;
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
    private final TdtGiangVienMapper lecturerMapper;

    @Override
    public Long countLecturer(LecturerCondition condition) {
        TdtGiangVienExample example = new TdtGiangVienExample();
        TdtGiangVienExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getLecturerIds())) {
            criteria.andIdIn(condition.getLecturerIds());
        }
        criteria.andDeletedFlagEqualTo(false);
        return lecturerMapper.countByExample(example);
    }

    @Override
    public List<TdtGiangVien> findLecturer(LecturerCondition condition) {
        TdtGiangVienExample example = new TdtGiangVienExample();
        TdtGiangVienExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getLecturerIds())) {
            criteria.andIdIn(condition.getLecturerIds());
        }
        Optional.ofNullable(condition.getEmailTdtu()).ifPresent(criteria::andEmailTdtuEqualTo);
        criteria.andDeletedFlagEqualTo(false);
        return lecturerMapper.selectByExample(example);
    }

    @Override
    public int update(TdtGiangVien record, BigDecimal lecturerId) {
        TdtGiangVienExample example = new TdtGiangVienExample();
        TdtGiangVienExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(lecturerId).ifPresent(criteria::andIdEqualTo);
        criteria.andDeletedFlagEqualTo(false);
        return lecturerMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int delete(LecturerCondition condition) {
        TdtGiangVienExample example = new TdtGiangVienExample();
        TdtGiangVienExample.Criteria criteria = example.createCriteria();
        if (ArrayUtil.isNotNullAndNotEmptyList(condition.getLecturerIds())) {
            criteria.andIdIn(condition.getLecturerIds());
        }
        criteria.andDeletedFlagEqualTo(false);

        TdtGiangVien record = TdtGiangVien.builder()
                .deletedFlag(true)
                .deletedAt(DateUtil.getTimeNow())
                .deletedBy(condition.getDeleteBy())
                .build();
        return lecturerMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int create(TdtGiangVien record) {
        return lecturerMapper.insertSelective(record);
    }
}
