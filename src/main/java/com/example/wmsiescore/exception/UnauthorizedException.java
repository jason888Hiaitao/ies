package com.example.wmsiescore.exception;

import com.example.wmsiescore.common.ResponseCode;

/**
 * 未授权异常
 */
public class UnauthorizedException extends BusinessException {
    
    public UnauthorizedException(String message) {
        super(ResponseCode.UNAUTHORIZED, message);
    }
    
    public UnauthorizedException() {
        super(ResponseCode.UNAUTHORIZED);
    }
}