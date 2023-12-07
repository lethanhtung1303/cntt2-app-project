package com.tdtu.webproject.mybatis.result;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LecturerSatisfactionScoreResult {
    private BigDecimal lecturerId;
    private BigDecimal semester;
    private BigDecimal satisfactionScore;
}
