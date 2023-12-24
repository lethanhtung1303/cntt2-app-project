package com.tdtu.webproject.constant;

import com.tdtu.webproject.utils.NumberUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public class Const {
    public static final int CONTRACTUAL_LECTURER = 1;
    public static final int VISITING_LECTURER = 0;
    public static final int LEVEL_MASTER = 4;
    public static final int LEVEL_DOCTORAL = 3;
    public static final int NORMAL_NUMBER_LESSONS_MASTER = 207;
    public static final int WARNING_NUMBER_LESSONS_MASTER = 350;
    public static final int NORMAL_NUMBER_LESSONS_DOCTORAL = 243;
    public static final int WARNING_NUMBER_LESSONS_DOCTORAL = 350;
    public static final int LECTURER_HOURS_WARNING = 0;
    public static final int LECTURER_HOURS_LEVELOUT = 1;
    public static final int LECTURER_HOURS_NORMAL = 2;
    public static final int LECTURER_HOURS_NOTTEACHING = 3;
    public static final double THEORY_CODE = 0;
    public static final double PRACTICE_CODE = 1;
    public static final String BASIC_SUBJECTS = "0";
    public static final int LESSONS_STANDARD_SYS = 1000;
    public static final int LESSONS_CLC_VIETNAMESE = 2100;
    public static final int LESSONS_CLC_ENGLISH = 2200;
    public static final int LESSONS_ENGLISH_INTERNATIONAL = 3000;
    public static final int LESSONS_MASTER = 4000;
    public static final double CONVERSION_COEFFICIENT_STANDARD_SYS = 1;
    public static final double CONVERSION_COEFFICIENT_CLC_VIETNAMESE = 1.1;
    public static final double CONVERSION_COEFFICIENT_CLC_ENGLISH = 1.2;
    public static final double CONVERSION_COEFFICIENT_ENGLISH_INTERNATIONAL = 1.2;
    public static final double CONVERSION_COEFFICIENT_MASTER = 1.3;
    public static final double CONVERSION_COEFFICIENT_THEORY = 1;
    public static final double CONVERSION_COEFFICIENT_PRACTICE = 0.85;
    public static final int STANDARD_SYS_RATE = 88000;
    public static final int LEVEL_MASTER_RATE = 120000;
    public static final int LEVEL_DOCTORAL_RATE = 140000;
    public static final int CLC_VIETNAMESE_BASIC_RATE = 160000;
    public static final int CLC_VIETNAMESE_MAJORING_RATE = 180000;
    public static final int CLC_ENGLISH_BASIC_RATE = 180000;
    public static final int CLC_ENGLISH_MAJORING_RATE = 200000;
    public static final int ENGLISH_INTL_BASIC_RATE = 180000;
    public static final int ENGLISH_INTL_MAJORING_RATE = 200000;
    public static final int MASTER_RATE = 232000;
    public static final String SUCCESSFUL = "SUCCESSFUL";
    public static final String FAIL = "FAIL";
    public static final String UNAPPROVE = "UNAPPROVE";
    public static final String APPROVE = "APPROVE";
    public static final int LANGUAGE_CODE_ENGLISH = 2;
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
