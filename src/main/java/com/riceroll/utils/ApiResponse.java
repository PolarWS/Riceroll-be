package com.riceroll.utils;

import com.riceroll.vo.comment.commentVO;
import lombok.Data;

import java.util.List;

@Data
public class ApiResponse<T> {
    private int status;
    private String message;
    private Long pages;
    private T data;

    public ApiResponse(int status, String message, Long pages,T data) {
        this.status = status;
        this.message = message;
        this.pages = pages;
        this.data = data;
    }

    // 快速创建成功响应
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "Success", null, data);
    }

    // 快速创建成功响应，自定义消息
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(200, message, null, data);
    }

    // 快速创建成功响应，包含页数
    public static <T> ApiResponse<T> success(T data, Long pages) {
        return new ApiResponse<>(200, "Success", pages, data);
    }

    // 快速创建失败响应
    public static <T> ApiResponse<T> fail(int status, String message) {
        return new ApiResponse<>(status, message, null, null);
    }

    // 快速创建失败响应，包含数据
    public static <T> ApiResponse<T> fail(int status, String message, T data) {
        return new ApiResponse<>(status, message, null, data);
    }

    // 快速创建失败响应，包含页数
    public static <T> ApiResponse<T> fail(int status, String message, T data, Long pages) {
        return new ApiResponse<>(status, message, pages, data);
    }
}
