package com.tdtu.webproject.repository;

import com.tdtu.webproject.model.condition.EmployeeCondition;
import com.tdtu.webproject.mybatis.result.EmployeeResult;

import java.util.List;

public interface EmployeeRepository {
    Long countEmployee(EmployeeCondition condition);

    List<EmployeeResult> findEmployee(EmployeeCondition condition);
}
