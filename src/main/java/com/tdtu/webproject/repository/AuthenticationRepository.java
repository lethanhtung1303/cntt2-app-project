package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtUserProfile;
import com.tdtu.webproject.mybatis.result.UserRolesResult;

import java.math.BigDecimal;
import java.util.List;

public interface AuthenticationRepository {
    TdtUserProfile findUser(String userName);

    int update(TdtUserProfile record, BigDecimal userId);

    List<UserRolesResult> loadRoleDetail(BigDecimal employeeID);
}
