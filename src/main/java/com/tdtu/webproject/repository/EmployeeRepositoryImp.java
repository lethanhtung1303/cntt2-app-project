package com.tdtu.webproject.repository;

import com.tdtu.webproject.model.condition.EmployeeCondition;
import com.tdtu.webproject.mybatis.mapper.EmployeeSupportMapper;
import com.tdtu.webproject.mybatis.result.EmployeeResult;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
@ComponentScan
public class EmployeeRepositoryImp implements EmployeeRepository {
    private final EmployeeSupportMapper employeeSupportMapper;

    @Override
    public Long countEmployee(EmployeeCondition condition) {
        return employeeSupportMapper.count(condition);
    }

    @Override
    public List<EmployeeResult> findEmployee(EmployeeCondition condition) {
        return employeeSupportMapper.find(condition);
    }
}
