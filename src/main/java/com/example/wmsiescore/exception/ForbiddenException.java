package com.example.wmsiescore.exception;

import com.example.wmsiescore.common.ResponseCode;

/**
 * 禁止访问异常
 */
public class ForbiddenException extends BusinessException {
    
    public ForbiddenException(String message) {
        super(ResponseCode.FORBIDDEN, message);
    }
    
    public ForbiddenException() {
        super(ResponseCode.FORBIDDEN);
    }
}