package com.tdtu.webproject.mybatis.result;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LecturerTeachingHistoryResult {
    private String subjectCode;
    private String subjectTitle;
    private BigDecimal numberLessons;
    private String subjectGroupCode;
    private String subjectGroupName;
    private BigDecimal subjectTypeCode;
    private String nameTypeSubject;
    private BigDecimal trainingSysCode;
    private String nameTrainingSys;
    private BigDecimal identification;
}
