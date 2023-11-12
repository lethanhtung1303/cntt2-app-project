package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtGiangVienExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtGiangVienMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtGiangVien;
import com.tdtu.webproject.model.condition.LecturerCondition;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

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
        Optional.ofNullable(condition.getLecturerIds()).ifPresent(criteria::andIdIn);
        criteria.andDeletedFlagEqualTo(false);
        return lecturerMapper.countByExample(example);
    }

    @Override
    public List<TdtGiangVien> findLecturer(LecturerCondition condition) {
        TdtGiangVienExample example = new TdtGiangVienExample();
        TdtGiangVienExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(condition.getLecturerIds()).ifPresent(criteria::andIdIn);
        criteria.andDeletedFlagEqualTo(false);
        return lecturerMapper.selectByExample(example);
    }
}
