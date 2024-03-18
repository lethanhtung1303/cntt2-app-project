package com.tdtu.webproject.mybatis.result;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UserRolesResult {
    private BigDecimal id;

    private BigDecimal idCategory;

    private String nameCategoryNameVN;

    private BigDecimal employeeID;

    private BigDecimal roleView;

    private BigDecimal roleUpdate;
    
    private BigDecimal roleDelete;
}
