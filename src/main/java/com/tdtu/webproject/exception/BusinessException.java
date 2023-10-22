package com.tdtu.webproject.exception;

import lombok.Getter;
@Getter
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -4741200958233543542L;
    private final String errMessage;
    private final String errorCode;

    public BusinessException(String message) {
        super(message);
        this.errMessage = message;
        this.errorCode = null;
    }
}
