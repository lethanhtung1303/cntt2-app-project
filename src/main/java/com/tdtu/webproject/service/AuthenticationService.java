package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtUserprofile;
import com.tdtu.webproject.exception.BusinessException;
import com.tdtu.webproject.mybatis.result.UserRolesResult;
import com.tdtu.webproject.repository.AuthenticationRepository;
import com.tdtu.webproject.utils.DateUtil;
import com.tdtu.webproject.utils.StringUtil;
import generater.openapi.model.LoginRequest;
import generater.openapi.model.User;
import generater.openapi.model.UserResponseResults;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder encoder;
    private final AuthenticationRepository authenticationRepository;

    public UserResponseResults findUser(LoginRequest loginRequest) {
        TdtUserprofile userprofile = authenticationRepository.findUser(loginRequest.getInputUserName());
        if (Optional.ofNullable(userprofile).isPresent()) {

            if (userprofile.getAccountstatus().equals(BigDecimal.ONE)) {
                throw new BusinessException("40002", "Tài khoản của bạn đã bị khóa , vui lòng liên hệ bộ phận quản lý !");
            }

            if (!encoder.matches(loginRequest.getInputPassword(), userprofile.getPassword())) {
                BigDecimal countLoginFailed = Optional.ofNullable(userprofile.getCountloginfailed()).orElse(BigDecimal.ZERO);
                userprofile.setCountloginfailed(countLoginFailed.add(BigDecimal.ONE));

                if (countLoginFailed.compareTo(BigDecimal.valueOf(4)) >= 0) {
                    userprofile.setAccountstatus(BigDecimal.ONE);
                    userprofile.setCountloginfailed(BigDecimal.ZERO);
                }

                authenticationRepository.update(userprofile, userprofile.getUserid());
                throw new BusinessException("40003", "Tài khoản hoặc mật khẩu không đúng , Bạn đã nhập sai " + (countLoginFailed.add(BigDecimal.ONE)) + " lần !");
            } else {
                if (userprofile.getAccountstatus().equals(BigDecimal.ONE)) {
                    throw new BusinessException("40002", "Tài khoản của bạn đã bị khóa , vui lòng liên hệ bộ phận quản lý !");
                } else {

                    userprofile.setIplastlogin(StringUtil.getLocalIPAddress());
                    userprofile.setDatelastlogin(DateUtil.getTimeNow());
                    authenticationRepository.update(userprofile, userprofile.getUserid());

                    return UserResponseResults.builder()
                            .user(User.builder()
                                    .userID(StringUtil.convertBigDecimalToString(userprofile.getUserid()))
                                    .userName(userprofile.getUsername())
                                    .employeeID(StringUtil.convertBigDecimalToString(userprofile.getEmployeeid()))
                                    .password(userprofile.getPassword())
                                    .isManagerment(userprofile.getIsmanagement() ? "1" : "0")
                                    .build())
                            .userRoles(this.loadRoleDetail(userprofile.getEmployeeid()))
                            .build();

                }
            }
        }
        throw new BusinessException("40001", "Tài khoản hoặc mật khẩu không đúng !");
    }

    private String loadRoleDetail(BigDecimal employeeId) {
        List<UserRolesResult> userRolesResult = authenticationRepository.loadRoleDetail(employeeId);
        StringBuilder userRoles = new StringBuilder("tdt_sys");

        for (UserRolesResult role : userRolesResult) {
            if (role.getIdCategory().compareTo(BigDecimal.valueOf(1)) == 0) {
                userRoles.append(" ").append(role.getRoleView().equals(BigDecimal.ONE) ? "tdt_sys_hp_v" : "");
                userRoles.append(" ").append(role.getRoleUpdate().equals(BigDecimal.ONE) ? "tdt_sys_hp_u" : "");
                userRoles.append(" ").append(role.getRoleDelete().equals(BigDecimal.ONE) ? "tdt_sys_hp_d" : "");
            }

            if (role.getIdCategory().compareTo(BigDecimal.valueOf(2)) == 0) {
                userRoles.append(" ").append(role.getRoleView().equals(BigDecimal.ONE) ? "tdt_sys_hr_v" : "");
                userRoles.append(" ").append(role.getRoleUpdate().equals(BigDecimal.ONE) ? "tdt_sys_hr_u" : "");
                userRoles.append(" ").append(role.getRoleDelete().equals(BigDecimal.ONE) ? "tdt_sys_hr_d" : "");
            }

            if (role.getIdCategory().compareTo(BigDecimal.valueOf(3)) == 0) {
                userRoles.append(" ").append(role.getRoleView().equals(BigDecimal.ONE) ? "tdt_sys_hrc_v" : "");
                userRoles.append(" ").append(role.getRoleUpdate().equals(BigDecimal.ONE) ? "tdt_sys_hrc_u" : "");
                userRoles.append(" ").append(role.getRoleDelete().equals(BigDecimal.ONE) ? "tdt_sys_hrc_d" : "");
            }

            if (role.getIdCategory().compareTo(BigDecimal.valueOf(4)) == 0) {
                userRoles.append(" ").append(role.getRoleView().equals(BigDecimal.ONE) ? "tdt_sys_s_v" : "");
                userRoles.append(" ").append(role.getRoleUpdate().equals(BigDecimal.ONE) ? "tdt_sys_s_u" : "");
                userRoles.append(" ").append(role.getRoleDelete().equals(BigDecimal.ONE) ? "tdt_sys_s_d" : "");
            }

            if (role.getIdCategory().compareTo(BigDecimal.valueOf(5)) == 0) {
                userRoles.append(" ").append(role.getRoleView().equals(BigDecimal.ONE) ? "tdt_sys_sc_v" : "");
                userRoles.append(" ").append(role.getRoleUpdate().equals(BigDecimal.ONE) ? "tdt_sys_sc_u" : "");
                userRoles.append(" ").append(role.getRoleDelete().equals(BigDecimal.ONE) ? "tdt_sys_sc_d" : "");
            }

            if (role.getIdCategory().compareTo(BigDecimal.valueOf(6)) == 0) {
                userRoles.append(" ").append(role.getRoleView().equals(BigDecimal.ONE) ? "tdt_sys_c_v" : "");
                userRoles.append(" ").append(role.getRoleUpdate().equals(BigDecimal.ONE) ? "tdt_sys_c_u" : "");
                userRoles.append(" ").append(role.getRoleDelete().equals(BigDecimal.ONE) ? "tdt_sys_c_d" : "");
            }

            if (role.getIdCategory().compareTo(BigDecimal.valueOf(7)) == 0) {
                userRoles.append(" ").append(role.getRoleView().equals(BigDecimal.ONE) ? "tdt_sys_cc_v" : "");
                userRoles.append(" ").append(role.getRoleUpdate().equals(BigDecimal.ONE) ? "tdt_sys_cc_u" : "");
                userRoles.append(" ").append(role.getRoleDelete().equals(BigDecimal.ONE) ? "tdt_sys_cc_d" : "");
            }

            if (role.getIdCategory().compareTo(BigDecimal.valueOf(8)) == 0) {
                userRoles.append(" ").append(role.getRoleView().equals(BigDecimal.ONE) ? "tdt_sys_ad_v" : "");
                userRoles.append(" ").append(role.getRoleUpdate().equals(BigDecimal.ONE) ? "tdt_sys_ad_u" : "");
                userRoles.append(" ").append(role.getRoleDelete().equals(BigDecimal.ONE) ? "tdt_sys_ad_d" : "");
            }
        }

        return userRoles.toString();
    }
}
