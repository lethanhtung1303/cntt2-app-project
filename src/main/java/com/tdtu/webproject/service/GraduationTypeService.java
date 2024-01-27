package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtLoaiTotNghiep;
import com.tdtu.webproject.repository.GraduationTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GraduationTypeService {
    private final GraduationTypeRepository graduationTypeRepository;

    public List<TdtLoaiTotNghiep> getAllGraduationType() {
        return graduationTypeRepository.getAllGraduationType();
    }
}
