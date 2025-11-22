package com.example.wmsiescore.common;

import com.example.wmsiescore.exception.BusinessException;
import lombok.Builder;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 错误详情类
 * 统一错误信息输出格式，包含错误码、类型、描述和解决方案建议
 */
@Data
@Builder
public class ErrorDetail {
    
    /**
     * 错误码
     */
    private int code;
    
    /**
     * 错误消息
     */
    private String message;
    
    /**
     * 错误类型
     */
    private String type;
    
    /**
     * 时间戳
     */
    private Long timestamp;
    
    /**
     * 请求路径
     */
    private String path;
    
    /**
     * 解决方案建议
     */
    private String suggestion;
    
    /**
     * 请求ID
     */
    private String requestId;
    
    /**
     * 静态工厂方法 - 根据ResponseCode和HttpServletRequest创建ErrorDetail
     */
    public static ErrorDetail of(ResponseCode responseCode, HttpServletRequest request) {
        return ErrorDetail.builder()
            .code(responseCode.getCode())
            .message(responseCode.getMessage())
            .type(ErrorCategory.fromCode(responseCode.getCode()).name())
            .timestamp(System.currentTimeMillis())
            .path(request != null ? request.getRequestURI() : "")
            .suggestion(responseCode.getSuggestion())
            .requestId(generateRequestId())
            .build();
    }
    
    /**
     * 静态工厂方法 - 根据异常和HttpServletRequest创建ErrorDetail
     */
    public static ErrorDetail of(Exception e, HttpServletRequest request) {
        if (e instanceof BusinessException) {
            BusinessException be = (BusinessException) e;
            return ErrorDetail.builder()
                .code(be.getCode())
                .message(be.getMessage())
                .type(ErrorCategory.fromCode(be.getCode()).name())
                .timestamp(System.currentTimeMillis())
                .path(request != null ? request.getRequestURI() : "")
                .suggestion(getSuggestion(be.getCode()))
                .requestId(generateRequestId())
                .build();
        } else {
            return ErrorDetail.builder()
                .code(ResponseCode.INTERNAL_SERVER_ERROR.getCode())
                .message(ResponseCode.INTERNAL_SERVER_ERROR.getMessage())
                .type(ErrorCategory.SYSTEM_ERROR.name())
                .timestamp(System.currentTimeMillis())
                .path(request != null ? request.getRequestURI() : "")
                .suggestion(ResponseCode.INTERNAL_SERVER_ERROR.getSuggestion())
                .requestId(generateRequestId())
                .build();
        }
    }
    
    /**
     * 根据错误码获取解决方案建议
     */
    private static String getSuggestion(int code) {
        try {
            ResponseCode responseCode = ResponseCode.fromCode(code);
            return responseCode.getSuggestion();
        } catch (Exception e) {
            return "请联系系统管理员";
        }
    }
    
    /**
     * 生成请求ID
     */
    private static String generateRequestId() {
        return "req-" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }
    
    /**
     * 转换为JSON格式字符串
     */
    public String toJsonString() {
        return String.format(
            "{\"code\":%d,\"message\":\"%s\",\"type\":\"%s\",\"timestamp\":%d,\"path\":\"%s\",\"suggestion\":\"%s\",\"requestId\":\"%s\"}",
            code, message, type, timestamp, path, suggestion, requestId
        );
    }
    
    /**
     * 获取错误分类描述
     */
    public String getCategoryDescription() {
        ErrorCategory category = ErrorCategory.fromCode(code);
        return category.getDescription();
    }
    
    /**
     * 验证错误详情是否有效
     */
    public boolean isValid() {
        return code > 0 && message != null && !message.trim().isEmpty();
    }
}