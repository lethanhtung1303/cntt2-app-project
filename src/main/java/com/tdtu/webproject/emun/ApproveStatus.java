package com.tdtu.webproject.emun;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ApproveStatus {
    CANCEL(0, "CANCEL"),
    UNAPPROVED(1, "UNAPPROVED"),
    APPROVED(2, "APPROVED");

    private final int Code;
    private final String Status;

    public static String getStatusByCode(int code) {
        return switch (code) {
            case 0 -> "CANCEL";
            case 2 -> "APPROVED";
            default -> "UNAPPROVED";
        };
    }

}
