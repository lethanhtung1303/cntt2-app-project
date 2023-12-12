package com.tdtu.webproject.constant;

import com.tdtu.webproject.utils.NumberUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public class Const {
    public static final int LECTURER_HOURS_WARNING = 0;
    public static final int LECTURER_HOURS_LEVELOUT = 1;
    public static final int LECTURER_HOURS_NORMAL = 2;
    public static final int LECTURER_HOURS_NOTTEACHING = 3;
    public static final double CONVERSION_COEFFICIENT_THEORY = 1;
    public static final double CONVERSION_COEFFICIENT_PRACTICE = 0.85;
    public static final String SUCCESSFUL = "SUCCESSFUL";
    public static final String FAIL = "FAIL";
    public static final String UNAPPROVE = "UNAPPROVE";
    public static final String APPROVE = "APPROVE";
    public static final String STANDARD_IELTS = "6.5";
    public static final String STANDARD_TOEFL_IBT = "80";
    public static final String STANDARD_TOEIC = "700";
    public static final String STANDARD_CAMBRIDGE = "180";
    public static final String STANDARD_APTIS_ESOL = "145";
    public static final TeachingSystem STANDARD_SYSTEM = TeachingSystem.builder()
            .id(NumberUtil.toBigDecimal("1").get())
            .name("Tiêu chuẩn")
            .build();
    public static final TeachingSystem HIGH_QUALITY_SYSTEM = TeachingSystem.builder()
            .id(NumberUtil.toBigDecimal("2").get())
            .name("Tiêu chuẩn")
            .build();
    public static final TeachingSystem ENGLISH_INTERNATIONAL_CONNECTION = TeachingSystem.builder()
            .id(NumberUtil.toBigDecimal("3").get())
            .name("Tiêu chuẩn")
            .build();

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class TeachingSystem {
        private BigDecimal id;
        private String name;
    }
}
