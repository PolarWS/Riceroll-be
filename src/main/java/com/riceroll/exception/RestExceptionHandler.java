package com.riceroll.exception;

import com.riceroll.utils.ApiResponse;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@ResponseBody
public class RestExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ApiResponse<Object> handleAllExceptions(Exception ex) {
        ex.printStackTrace();
        return ApiResponse.fail(500, "服务器错误");
    }

    @ExceptionHandler(NullPointerException.class)
    public ApiResponse<Object> handleNullPointer(NullPointerException ex) {
        ex.printStackTrace();
        return ApiResponse.fail(500, "服务器错误");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Object> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ApiResponse.fail(400, errorMessage);
    }

    @ExceptionHandler(RequestNotPermitted.class)
    public ApiResponse<Object> handleRateLimiterException(RequestNotPermitted e) {
        return ApiResponse.fail(400, "请求过于频繁");
    }
}
