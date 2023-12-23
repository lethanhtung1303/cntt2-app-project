package com.tdtu.webproject.mybatis.condition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class SubjectCondition {
    private List<String> subjectIds;
    private String deleteBy;
}
