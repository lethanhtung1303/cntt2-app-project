package com.tdtu.webproject.mybatis.result;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LecturerTrainingLanguageResult {
    private BigDecimal trainingProcessId;
    private BigDecimal lecturerId;
    private BigDecimal languageCode;
    private String languageName;
}
