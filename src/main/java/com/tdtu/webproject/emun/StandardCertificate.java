package com.tdtu.webproject.emun;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StandardCertificate {
    IELTS(1, "6.5"),
    TOEFL_IBT(2, "80"),
    TOEIC(3, "700"),
    CAMBRIDGE(4, "180"),
    APTIS_ESOL(5, "145");

    private final int certificateCode;
    private final String certificateScore;

    public static String getScoreByCode(int code) {
        return switch (code) {
            case 1 -> "6.5";
            case 2 -> "80";
            case 3 -> "700";
            case 4 -> "180";
            case 5 -> "145";
            default -> null;
        };
    }

}
