package com.xushu.bookmanage.exception;

import com.xushu.bookmanage.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ServiceException.class, produces = "application/json;charset=UTF-8")
    public Result<?> handleServiceException(ServiceException e) {
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class, produces = "application/json;charset=UTF-8")
    public Result<?> handleException(Exception e) {
        e.printStackTrace();
        return Result.error(500, "系统未知错误，请联系管理员");
    }
}
