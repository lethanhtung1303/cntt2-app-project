package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtLoaiGiangVienExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtLoaiGiangVienMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtLoaiGiangVien;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
@ComponentScan
public class ClassificationLecturerRepositoryImp implements ClassificationLecturerRepository {
    private final TdtLoaiGiangVienMapper classificationLecturerMapper;

    @Override
    public Long countAllClassification() {
        TdtLoaiGiangVienExample example = new TdtLoaiGiangVienExample();
        return classificationLecturerMapper.countByExample(example);
    }

    @Override
    public List<TdtLoaiGiangVien> getAllClassification() {
        TdtLoaiGiangVienExample example = new TdtLoaiGiangVienExample();
        return classificationLecturerMapper.selectByExample(example);
    }
}
