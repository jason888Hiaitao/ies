package com.example.wmsiescore.exception;

import com.example.wmsiescore.common.ResponseCode;

/**
 * 参数验证异常
 */
public class ParameterValidationException extends BusinessException {
    
    public ParameterValidationException(String fieldName, String errorMessage) {
        super(ResponseCode.BAD_REQUEST, String.format("参数验证失败 - %s: %s", fieldName, errorMessage));
    }
    
    public ParameterValidationException(String message) {
        super(ResponseCode.BAD_REQUEST, message);
    }
}