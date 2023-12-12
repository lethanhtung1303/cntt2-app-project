package com.tdtu.webproject.emun;

import com.tdtu.webproject.utils.NumberUtil;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public enum LectureHoursStatus {
    WARNING(0, "Cảnh báo", NumberUtil.toBigDecimal("0").get()),
    LEVELOUT(1, "Vượt mức", NumberUtil.toBigDecimal("1").get()),
    NORMAL(2, "Bình thường", NumberUtil.toBigDecimal("2").get()),
    NOTTEACHING(3, "Chưa giảng dạy", NumberUtil.toBigDecimal("3").get());

    private final int Code;
    private final String Status;
    private final BigDecimal Value;

    public static String getStatusByCode(int code) {
        return switch (code) {
            case 0 -> WARNING.Status;
            case 1 -> LEVELOUT.Status;
            case 2 -> NORMAL.Status;
            default -> NOTTEACHING.Status;
        };
    }

    public static BigDecimal getValueByCode(int code) {
        return switch (code) {
            case 0 -> WARNING.Value;
            case 1 -> LEVELOUT.Value;
            case 2 -> NORMAL.Value;
            default -> NOTTEACHING.Value;
        };
    }
}
