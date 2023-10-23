package com.tdtu.webproject.mybatis.mapper;

import com.tdtu.webproject.model.condition.EmployeeCondition;
import com.tdtu.webproject.mybatis.result.EmployeeResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeSupportMapper {
    Long count(EmployeeCondition condition);

    List<EmployeeResult> find(EmployeeCondition condition);
}
