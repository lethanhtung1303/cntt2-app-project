package com.tdtu.webproject.service;

import com.tdtu.webproject.mybatis.condition.HistoryTeachingCondition;
import com.tdtu.webproject.repository.HistoryTeachingRepository;
import generater.openapi.model.Assignment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.tdtu.webproject.constant.Const.MAXIMUM_ASSIGNMENTS;

@Service
@AllArgsConstructor
public class AssignmentManageService {
    private final HistoryTeachingRepository historyTeachingRepository;

    public boolean checkMaximumAssignments(Assignment assignment) {
        HistoryTeachingCondition condition = HistoryTeachingCondition.builder()
                .lecturerId(assignment.getGiangVienId())
                .semester(assignment.getHocKy())
                .build();
        return historyTeachingRepository.countHistoryTeaching(condition) >= MAXIMUM_ASSIGNMENTS;
    }

    public boolean checkAssignments(Assignment assignment) {
        HistoryTeachingCondition condition = HistoryTeachingCondition.builder()
                .lecturerId(assignment.getGiangVienId())
                .semester(assignment.getHocKy())
                .subjectId(assignment.getMaMon())
                .trainingSysId(assignment.getMaHe())
                .build();
        return historyTeachingRepository.countHistoryTeaching(condition) < 1;
    }
}
