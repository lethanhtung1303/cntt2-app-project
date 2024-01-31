package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtDegree;
import com.tdtu.webproject.repository.LevelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LevelService {
    private final LevelRepository levelRepository;

    public List<TdtDegree> getAllLevel() {
        return levelRepository.getAllLevel();
    }
}
