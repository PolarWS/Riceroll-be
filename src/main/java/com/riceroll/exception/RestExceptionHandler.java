package com.riceroll.exception;

import com.riceroll.utils.ApiResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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
        //空指针异常处理
        ex.printStackTrace();
        return ApiResponse.fail(500, "服务器错误");
    }
}
