package com.tdtu.webproject.model.condition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class EmployeeCondition {
    private BigDecimal EmployeeId;
}
