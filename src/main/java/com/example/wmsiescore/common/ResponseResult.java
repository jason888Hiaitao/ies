package com.example.wmsiescore.common;

import lombok.Data;

/**
 * 统一响应结果封装类
 */
@Data
public class ResponseResult<T> {
    
    /**
     * 响应码
     */
    private Integer code;
    
    /**
     * 响应消息
     */
    private String message;
    
    /**
     * 响应数据
     */
    private T data;
    
    /**
     * 时间戳
     */
    private Long timestamp;
    
    public ResponseResult() {
        this.timestamp = System.currentTimeMillis();
    }
    
    public ResponseResult(Integer code, String message) {
        this();
        this.code = code;
        this.message = message;
    }
    
    public ResponseResult(Integer code, String message, T data) {
        this(code, message);
        this.data = data;
    }
    
    /**
     * 成功响应
     */
    public static <T> ResponseResult<T> success() {
        return new ResponseResult<>(200, "操作成功");
    }
    
    /**
     * 成功响应（带数据）
     */
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(200, "操作成功", data);
    }
    
    /**
     * 成功响应（自定义消息）
     */
    public static <T> ResponseResult<T> success(String message, T data) {
        return new ResponseResult<>(200, message, data);
    }
    
    /**
     * 失败响应
     */
    public static <T> ResponseResult<T> fail() {
        return new ResponseResult<>(500, "操作失败");
    }
    
    /**
     * 失败响应（带错误信息）
     */
    public static <T> ResponseResult<T> fail(String message) {
        // 参数验证
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("错误信息不能为空");
        }
        return new ResponseResult<>(500, message);
    }
    
    /**
     * 失败响应（自定义状态码和错误信息）
     */
    public static <T> ResponseResult<T> fail(Integer code, String message) {
        // 参数验证
        if (code == null) {
            throw new IllegalArgumentException("状态码不能为空");
        }
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("错误信息不能为空");
        }
        return new ResponseResult<>(code, message);
    }
    
    /**
     * 失败响应（带数据）
     */
    public static <T> ResponseResult<T> fail(String message, T data) {
        // 参数验证
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("错误信息不能为空");
        }
        return new ResponseResult<>(500, message, data);
    }
    
    /**
     * 失败响应（自定义状态码、错误信息和数据）
     */
    public static <T> ResponseResult<T> fail(Integer code, String message, T data) {
        // 参数验证
        if (code == null) {
            throw new IllegalArgumentException("状态码不能为空");
        }
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("错误信息不能为空");
        }
        return new ResponseResult<>(code, message, data);
    }
    
    /**
     * 失败响应（已废弃的错误方法）
     */
    public static <T> ResponseResult<T> error() {
        return new ResponseResult<>(500, "操作失败");
    }
    
    /**
     * 失败响应（自定义消息）
     */
    public static <T> ResponseResult<T> error(String message) {
        return new ResponseResult<>(500, message);
    }
    
    /**
     * 失败响应（自定义码和消息）
     */
    public static <T> ResponseResult<T> error(Integer code, String message) {
        return new ResponseResult<>(code, message);
    }
    
    /**
     * 参数错误响应
     */
    public static <T> ResponseResult<T> paramError(String message) {
        return new ResponseResult<>(400, message);
    }
    
    /**
     * 未授权响应
     */
    public static <T> ResponseResult<T> unauthorized(String message) {
        return new ResponseResult<>(401, message);
    }
    
    /**
     * 禁止访问响应
     */
    public static <T> ResponseResult<T> forbidden(String message) {
        return new ResponseResult<>(403, message);
    }
    
    /**
     * 资源不存在响应
     */
    public static <T> ResponseResult<T> notFound(String message) {
        return new ResponseResult<>(404, message);
    }
}