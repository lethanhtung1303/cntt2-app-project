package com.tdtu.webproject.model.condition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class LecturerTeachingHistoryCondition {
    private BigDecimal lecturerId;
    private BigDecimal semester;
}
