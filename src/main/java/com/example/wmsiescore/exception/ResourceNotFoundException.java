package com.example.wmsiescore.exception;

import com.example.wmsiescore.common.ResponseCode;

/**
 * 资源未找到异常
 */
public class ResourceNotFoundException extends BusinessException {
    
    public ResourceNotFoundException(String resourceName, Object resourceId) {
        super(ResponseCode.NOT_FOUND, String.format("%s不存在: %s", resourceName, resourceId));
    }
    
    public ResourceNotFoundException(String message) {
        super(ResponseCode.NOT_FOUND, message);
    }
}