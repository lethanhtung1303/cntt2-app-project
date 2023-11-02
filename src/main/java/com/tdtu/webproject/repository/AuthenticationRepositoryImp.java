package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtUserprofileExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtUserprofileMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtUserprofile;
import com.tdtu.webproject.mybatis.mapper.UserRolesSupportMapper;
import com.tdtu.webproject.mybatis.result.UserRolesResult;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@ComponentScan
public class AuthenticationRepositoryImp implements AuthenticationRepository {
    private final TdtUserprofileMapper userProfileMapper;
    private final UserRolesSupportMapper userRolesSupportMapper;

    @Override
    public TdtUserprofile findUser(String userName) {
        TdtUserprofileExample example = new TdtUserprofileExample();
        TdtUserprofileExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(userName).ifPresent(criteria::andUsernameEqualTo);
        criteria.andIsactiveEqualTo(true);
        return userProfileMapper.selectByExample(example).stream()
                .filter(userProfile -> userProfile.getUsername().equals(userName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public int update(TdtUserprofile record, BigDecimal userId) {
        TdtUserprofileExample example = new TdtUserprofileExample();
        TdtUserprofileExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(userId).ifPresent(criteria::andUseridEqualTo);
        return userProfileMapper.updateByExampleSelective(record, example);
    }

    @Override
    public List<UserRolesResult> loadRoleDetail(BigDecimal employeeID) {
        return userRolesSupportMapper.loadRoleDetail(employeeID);
    }
}
