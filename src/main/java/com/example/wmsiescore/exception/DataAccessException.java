package com.example.wmsiescore.exception;

import com.example.wmsiescore.common.ResponseCode;

/**
 * 数据访问异常
 */
public class DataAccessException extends BusinessException {
    
    public DataAccessException(String message) {
        super(ResponseCode.INTERNAL_SERVER_ERROR, "数据访问异常: " + message);
    }
    
    public DataAccessException(String message, Throwable cause) {
        super(ResponseCode.INTERNAL_SERVER_ERROR, "数据访问异常: " + message);
        this.initCause(cause);
    }
}