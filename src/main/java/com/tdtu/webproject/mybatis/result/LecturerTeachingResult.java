package com.tdtu.webproject.mybatis.result;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LecturerTeachingResult {
    private BigDecimal lecturerId;
    private String subjectCode;
    private String subjectName;
    private BigDecimal numberLessons;
    private BigDecimal subjectType;
}
