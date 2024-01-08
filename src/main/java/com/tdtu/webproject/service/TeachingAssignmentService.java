package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtLichSuGiangDay;
import com.tdtu.webproject.exception.BusinessException;
import com.tdtu.webproject.repository.HistoryTeachingRepository;
import com.tdtu.webproject.utils.DateUtil;
import com.tdtu.webproject.utils.MessageProperties;
import com.tdtu.webproject.utils.StringUtil;
import generater.openapi.model.Assignment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.tdtu.webproject.constant.Const.*;

@Service
@AllArgsConstructor
public class TeachingAssignmentService {
    private final LecturerManageService lecturerManageService;
    private final AssignmentManageService assignmentManageService;
    private final HistoryTeachingRepository historyTeachingRepository;

    public String createAssignment(Assignment assignmentCreate, String createBy) {
        BigDecimal lecturerId = assignmentCreate.getGiangVienId();
        if (lecturerManageService.checkNotExistLecturer(lecturerId)) {
            throw new BusinessException("40001",
                    MessageProperties.getInstance().getProperty(LECTURER_NOT_FOUND, StringUtil.convertBigDecimalToString(lecturerId))
            );
        }
        if (assignmentManageService.checkMaximumAssignments(assignmentCreate)) {
            throw new BusinessException("40002",
                    MessageProperties.getInstance().getProperty(LECTURERS_MAXIMUM_ASSIGNMENTS, String.valueOf(MAXIMUM_ASSIGNMENTS))
            );
        }
        if (!assignmentManageService.checkAssignments(assignmentCreate)) {
            throw new BusinessException("40003",
                    MessageProperties.getInstance().getProperty(SUBJECT_ASSIGNED)
            );
        }
        TdtLichSuGiangDay teachingAssignment = this.buildTdtLichSuGiangDayForCreate(assignmentCreate, createBy);
        return historyTeachingRepository.create(teachingAssignment) > 0
                ? SUCCESSFUL
                : FAIL;
    }

    private TdtLichSuGiangDay buildTdtLichSuGiangDayForCreate(Assignment assignmentCreate, String createBy) {
        return TdtLichSuGiangDay.builder()
                .giangVienId(assignmentCreate.getGiangVienId())
                .hocKy(assignmentCreate.getHocKy())
                .maMon(assignmentCreate.getMaMon())
                .maHe(assignmentCreate.getMaHe())
                .createdBy(createBy)
                .createdAt(DateUtil.getTimeNow())
                .build();
    }
}
