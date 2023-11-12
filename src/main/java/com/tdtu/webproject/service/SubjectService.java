package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtMonHoc;
import com.tdtu.webproject.repository.SubjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public List<TdtMonHoc> findSubjectByMaMon(String maMon) {
        return subjectRepository.findSubjectByMaMon(maMon);
    }
}
