package com.tdtu.webproject.mybatis.condition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class LecturerTeachingHistoryCondition {
    private BigDecimal historyId;
    private BigDecimal lecturerId;
    private BigDecimal semester;
}
