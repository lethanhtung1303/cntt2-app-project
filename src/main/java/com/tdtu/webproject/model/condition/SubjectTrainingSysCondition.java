package com.tdtu.webproject.model.condition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class SubjectTrainingSysCondition {
    private List<BigDecimal> trainingSysIds;
}
