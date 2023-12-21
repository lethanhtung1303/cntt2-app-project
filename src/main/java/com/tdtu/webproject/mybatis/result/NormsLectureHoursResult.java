package com.tdtu.webproject.mybatis.result;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class NormsLectureHoursResult {
    private BigDecimal lecturerId;
    private String fullName;
    private String images;
    private String emailTdtu;
    private BigDecimal lecturerTypeCode;
    private String lecturerType;
    private BigDecimal levelCode;
    private String level;
    private BigDecimal displayOrder;
    private BigDecimal semester;
    private BigDecimal historyTeachingId;
    private String subjectCode;
    private BigDecimal numberLessons;
    private BigDecimal subjectTypeCode;
    private BigDecimal trainingSystem;
    private String subjectGroupCode;
    private LocalDateTime createDatetime;
}
