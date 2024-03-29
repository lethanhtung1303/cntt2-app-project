package com.tdtu.webproject.mybatis.condition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class SatisfactionScoreCondition {
    private List<BigDecimal> lecturerIds;
    private List<BigDecimal> satisfactionScoreIds;
    private String updateBy;
}
