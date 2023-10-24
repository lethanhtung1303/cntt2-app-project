package com.tdtu.webproject.service;

import com.tdtu.webproject.model.condition.EmployeeCondition;
import com.tdtu.webproject.mybatis.result.EmployeeResult;
import com.tdtu.webproject.repository.EmployeeRepository;
import com.tdtu.webproject.utils.DateUtil;
import com.tdtu.webproject.utils.NumberUtil;
import generater.openapi.model.Department;
import generater.openapi.model.EmployeeDetailResponse;
import generater.openapi.model.Position;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public Long count(String employeeIds) {
        EmployeeCondition condition = this.buildEmployeeCondition(employeeIds);
        return employeeRepository.countEmployee(condition);
    }

    public List<EmployeeDetailResponse> find(String employeeIds) {
        EmployeeCondition condition = this.buildEmployeeCondition(employeeIds);
        List<EmployeeResult> employeeResultList = employeeRepository.findEmployee(condition);
        return Optional.ofNullable(employeeResultList).isPresent()
                ? employeeResultList.stream()
                .map(this::buildEmployeeDetailResponse)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private EmployeeDetailResponse buildEmployeeDetailResponse(EmployeeResult result) {
        return EmployeeDetailResponse.builder()
                .ID(result.getEmployeeId())
                .employeeCode(result.getEmployeeCode())
                .firstName(result.getFirstName())
                .fullName(result.getFullName())
                .commonName(result.getCommonName())
                .gender(result.getGender())
                .images(result.getImages())
                .birthday(Optional.ofNullable(result.getBirthday()).isPresent()
                        ? DateUtil.getValueFromLocalDateTime(result.getBirthday(), DateUtil.YYYYMMDD_FORMAT_SLASH)
                        : null)
                .placeOfBirth(result.getPlaceOfBirth())
                .married(result.getMarried())
                .address(result.getAddress())
                .addressTmp(result.getAddressTmp())
                .phone(result.getPhone())
                .email(result.getEmail())
                .CCCD(result.getCCCD())
                .dateCCCD(Optional.ofNullable(result.getDateCCCD()).isPresent()
                        ? DateUtil.getValueFromLocalDateTime(result.getDateCCCD(), DateUtil.YYYYMMDD_FORMAT_SLASH)
                        : null)
                .issuedBy(result.getIssuedBy())
                .dateStartWord(Optional.ofNullable(result.getDateStartWord()).isPresent()
                        ? DateUtil.getValueFromLocalDateTime(result.getDateStartWord(), DateUtil.YYYYMMDD_FORMAT_SLASH)
                        : null)
                .health(result.getHealth())
                .height(result.getHeight())
                .weight(result.getWeight())
                .statusWork(result.getStatusWork())
                .nationalityID(result.getNationalityId())
                .nationID(result.getNationId())
                .religionID(result.getReligionId())
                .degreeID(result.getDegreeId())
                .foreignID(result.getForeignId())
                .BHXH(result.getBHXH())
                .BHYT(result.getBHYT())
                .BHTN(result.getBHTN())
                .unionDues(result.getUnionDues())
                .authority(result.getAuthority())
                .note(result.getNote())
                .createdByUser(result.getCreatedByUser())
                .createdByDate(Optional.ofNullable(result.getCreatedByDate()).isPresent()
                        ? DateUtil.getValueFromLocalDateTime(result.getCreatedByDate(), DateUtil.DATETIME_FORMAT_SLASH)
                        : null)
                .maritalID(result.getMaritalId())
                .comment(result.getComment())
                .department(Department.builder()
                        .ID(result.getDepartmentId())
                        .nameDepartmentVN(result.getNameDepartmentVN())
                        .nameDepartmentEN(result.getNameDepartmentEN())
                        .isActive(result.getIsActiveDepartment())
                        .dateCreated(Optional.ofNullable(result.getDepartmentDateCreated()).isPresent()
                                ? DateUtil.getValueFromLocalDateTime(result.getDepartmentDateCreated(), DateUtil.DATETIME_FORMAT_SLASH)
                                : null)
                        .userCreated(result.getDepartmentUserCreated())
                        .dateUpdated(Optional.ofNullable(result.getDepartmentDateUpdated()).isPresent()
                                ? DateUtil.getValueFromLocalDateTime(result.getDepartmentDateUpdated(), DateUtil.DATETIME_FORMAT_SLASH)
                                : null)
                        .userUpdated(result.getDepartmentUserUpdated())
                        .build())
                .dateUpdated(Optional.ofNullable(result.getDateUpdated()).isPresent()
                        ? DateUtil.getValueFromLocalDateTime(result.getDateUpdated(), DateUtil.DATETIME_FORMAT_SLASH)
                        : null)
                .userLastUpdated(result.getUserLastUpdated())
                .position(Position.builder()
                        .ID(result.getPositionId())
                        .positionNameVN(result.getPositionNameVN())
                        .positionNameEN(result.getPositionNameEN())
                        .isActive(result.getIsActivePosition())
                        .dateCreated(Optional.ofNullable(result.getPositionDateCreated()).isPresent()
                                ? DateUtil.getValueFromLocalDateTime(result.getPositionDateCreated(), DateUtil.DATETIME_FORMAT_SLASH)
                                : null)
                        .userCreated(result.getPositionUserCreated())
                        .dateUpdated(Optional.ofNullable(result.getPositionDateUpdated()).isPresent()
                                ? DateUtil.getValueFromLocalDateTime(result.getPositionDateUpdated(), DateUtil.DATETIME_FORMAT_SLASH)
                                : null)
                        .userUpdated(result.getPositionUserUpdated())
                        .build())
                .companyID(result.getCompanyId())
                .noBHXH(result.getNoBHXH())
                .dateBHXH(Optional.ofNullable(result.getDateBHXH()).isPresent()
                        ? DateUtil.getValueFromLocalDateTime(result.getDateBHXH(), DateUtil.YYYYMMDD_FORMAT_SLASH)
                        : null)
                .issueByBHXH(result.getIssueByBHXH())
                .noBHYT(result.getNoBHYT())
                .fromDateBHYT(Optional.ofNullable(result.getFromDateBHYT()).isPresent()
                        ? DateUtil.getValueFromLocalDateTime(result.getFromDateBHYT(), DateUtil.YYYYMMDD_FORMAT_SLASH)
                        : null)
                .toDateBHYT(Optional.ofNullable(result.getToDateBHYT()).isPresent()
                        ? DateUtil.getValueFromLocalDateTime(result.getToDateBHYT(), DateUtil.YYYYMMDD_FORMAT_SLASH)
                        : null)
                .provinceBHYT(result.getProvinceBHYT())
                .hopitalBHYT(result.getHopitalBHYT())
                .isActive(result.getIsActive())
                .leaderID(result.getLeaderId())
                .managerID(result.getManagerId())
                .salary(result.getSalary())
                .build();
    }

    private EmployeeCondition buildEmployeeCondition(String employeeIds) {
        return EmployeeCondition.builder()
                .employeeIds(Optional.ofNullable(employeeIds).isPresent()
                        ? Arrays.stream(employeeIds.split(","))
                        .map(contact -> NumberUtil.toBigDeimal(contact).orElse(null))
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                .build();
    }
}
