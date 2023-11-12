package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtUserProfileExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtUserProfileMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtUserProfile;
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
    private final TdtUserProfileMapper userProfileMapper;
    private final UserRolesSupportMapper userRolesSupportMapper;

    @Override
    public TdtUserProfile findUser(String userName) {
        TdtUserProfileExample example = new TdtUserProfileExample();
        TdtUserProfileExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(userName).ifPresent(criteria::andUserNameEqualTo);
        criteria.andIsActiveEqualTo(true);
        return userProfileMapper.selectByExample(example).stream()
                .filter(userProfile -> userProfile.getUserName().equals(userName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public int update(TdtUserProfile record, BigDecimal userId) {
        TdtUserProfileExample example = new TdtUserProfileExample();
        TdtUserProfileExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(userId).ifPresent(criteria::andUserIdEqualTo);
        return userProfileMapper.updateByExampleSelective(record, example);
    }

    @Override
    public List<UserRolesResult> loadRoleDetail(BigDecimal employeeID) {
        return userRolesSupportMapper.loadRoleDetail(employeeID);
    }
}
