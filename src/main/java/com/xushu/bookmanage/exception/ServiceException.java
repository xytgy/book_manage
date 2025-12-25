package com.xushu.bookmanage.exception;

public class ServiceException extends RuntimeException {
    private final Integer code;

    public ServiceException(String message) {
        super(message);
        this.code = 500;
    }

    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
