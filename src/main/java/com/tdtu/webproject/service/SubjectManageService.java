package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtSubject;
import com.tdtu.webproject.mybatis.condition.SubjectCondition;
import com.tdtu.webproject.repository.SubjectRepository;
import com.tdtu.webproject.utils.ArrayUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubjectManageService {

    private final SubjectRepository SubjectRepository;

    public boolean checkExistSubject(String subjectId) {
        List<TdtSubject> subjectList = SubjectRepository
                .findSubject(
                        SubjectCondition.builder()
                                .subjectIds(List.of(subjectId))
                                .build());
        return ArrayUtil.isNotNullAndNotEmptyList(subjectList);
    }
}
