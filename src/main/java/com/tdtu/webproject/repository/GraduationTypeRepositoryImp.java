package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtLoaiTotNghiepExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtLoaiTotNghiepMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtLoaiTotNghiep;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
@ComponentScan
public class GraduationTypeRepositoryImp implements GraduationTypeRepository {
    private final TdtLoaiTotNghiepMapper graduationTypeMapper;

    @Override
    public Long countAllGraduationType() {
        TdtLoaiTotNghiepExample example = new TdtLoaiTotNghiepExample();
        TdtLoaiTotNghiepExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return graduationTypeMapper.countByExample(example);
    }

    @Override
    public List<TdtLoaiTotNghiep> getAllGraduationType() {
        TdtLoaiTotNghiepExample example = new TdtLoaiTotNghiepExample();
        TdtLoaiTotNghiepExample.Criteria criteria = example.createCriteria();
        criteria.andIsActiveEqualTo(true);
        return graduationTypeMapper.selectByExample(example);
    }
}
