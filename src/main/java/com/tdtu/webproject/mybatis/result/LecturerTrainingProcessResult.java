package com.tdtu.webproject.mybatis.result;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LecturerTrainingProcessResult {
    private BigDecimal trainingProcessId;
    private BigDecimal lecturerId;
    private String fullName;
    private String images;
    private String emailTdtu;
    private String school;
    private String majors;
    private BigDecimal graduationYear;
    private String graduationType;
    private BigDecimal graduationTypeCode;
    private String level;
    private BigDecimal displayOrder;
}
