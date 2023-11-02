package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtUserprofile;
import com.tdtu.webproject.mybatis.result.UserRolesResult;

import java.math.BigDecimal;
import java.util.List;

public interface AuthenticationRepository {
    TdtUserprofile findUser(String userName);

    int update(TdtUserprofile record, BigDecimal userId);

    List<UserRolesResult> loadRoleDetail(BigDecimal employeeID);
}
