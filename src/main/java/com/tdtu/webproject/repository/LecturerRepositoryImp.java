package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtGiangVienExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtGiangVienMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtGiangVien;
import com.tdtu.webproject.model.condition.LecturerCondition;
import com.tdtu.webproject.utils.ArrayUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
@ComponentScan
public class LecturerRepositoryImp implements LecturerRepository {
    private final TdtGiangVienMapper lecturerMapper;

    @Override
    public Long countLecturer(LecturerCondition condition) {
        TdtGiangVienExample example = new TdtGiangVienExample();
        TdtGiangVienExample.Criteria criteria = example.createCriteria();
        if (!ArrayUtil.isNotNullOrEmptyList(condition.getLecturerIds())) {
            criteria.andIdIn(condition.getLecturerIds());
        }
        criteria.andDeletedFlagEqualTo(false);
        return lecturerMapper.countByExample(example);
    }

    @Override
    public List<TdtGiangVien> findLecturer(LecturerCondition condition) {
        TdtGiangVienExample example = new TdtGiangVienExample();
        TdtGiangVienExample.Criteria criteria = example.createCriteria();
        if (!ArrayUtil.isNotNullOrEmptyList(condition.getLecturerIds())) {
            criteria.andIdIn(condition.getLecturerIds());
        }
        criteria.andDeletedFlagEqualTo(false);
        return lecturerMapper.selectByExample(example);
    }
}
