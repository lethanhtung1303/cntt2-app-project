package com.tdtu.webproject.mybatis.result;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LecturerCertificateResult {
    private BigDecimal lecturerId;
    private String certificateScore;
    private BigDecimal certificateCode;
    private String certificateName;
}
