package com.tdtu.webproject.mybatis.result;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class EmployeeResult {
    private BigDecimal employeeId;

    private String employeeCode;

    private String firstName;

    private String fullName;

    private String commonName;

    private Boolean gender;

    private String images;

    private LocalDateTime birthday;

    private String placeOfBirth;

    private Boolean married;

    private String address;

    private String addressTmp;

    private String phone;

    private String email;

    private String CCCD;

    private LocalDateTime dateCCCD;

    private String issuedBy;

    private LocalDateTime dateStartWord;

    private String health;

    private BigDecimal height;

    private BigDecimal weight;

    private BigDecimal statusWork;

    private BigDecimal nationalityId;

    private BigDecimal nationId;

    private BigDecimal religionId;

    private BigDecimal degreeId;

    private String foreignId;

    private Boolean BHXH;

    private Boolean BHYT;

    private Boolean BHTN;

    private Boolean unionDues;

    private String note;

    private BigDecimal maritalId;

    private Boolean authority;

    private String comment;

    private BigDecimal companyId;

    private String noBHXH;

    private LocalDateTime dateBHXH;

    private String issueByBHXH;

    private String noBHYT;

    private LocalDateTime fromDateBHYT;

    private LocalDateTime toDateBHYT;

    private String provinceBHYT;

    private String hopitalBHYT;

    private Boolean isActive;

    private BigDecimal leaderId;

    private BigDecimal managerId;

    private BigDecimal salary;

    private String createdByUser;

    private LocalDateTime createdByDate;

    private LocalDateTime dateUpdated;

    private String userLastUpdated;

    //    Department

    private BigDecimal departmentId;

    private String nameDepartmentVN;

    private String nameDepartmentEN;

    private Boolean isActiveDepartment;

    private LocalDateTime departmentDateCreated;

    private String departmentUserCreated;

    private LocalDateTime departmentDateUpdated;

    private String departmentUserUpdated;

    //    Position

    private BigDecimal positionId;

    private String positionNameVN;

    private String positionNameEN;

    private Boolean isActivePosition;

    private LocalDateTime positionDateCreated;

    private String positionUserCreated;

    private LocalDateTime positionDateUpdated;

    private String positionUserUpdated;
}
