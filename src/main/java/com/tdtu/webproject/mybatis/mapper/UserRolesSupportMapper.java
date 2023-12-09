package com.tdtu.webproject.mybatis.mapper;

import com.tdtu.webproject.mybatis.result.UserRolesResult;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface UserRolesSupportMapper {
    List<UserRolesResult> loadRoleDetail(BigDecimal employeeID);
}
