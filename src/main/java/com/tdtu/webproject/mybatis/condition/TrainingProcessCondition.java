package com.tdtu.webproject.mybatis.condition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class TrainingProcessCondition {
    private List<BigDecimal> lecturerIds;
    private List<BigDecimal> processIds;
    private String updateBy;
}
