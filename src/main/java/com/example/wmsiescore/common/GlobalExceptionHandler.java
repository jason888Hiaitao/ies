package com.example.wmsiescore.common;

import com.example.wmsiescore.exception.*;
import com.example.wmsiescore.util.ExceptionLogUtil;
import com.example.wmsiescore.util.ExceptionMonitorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * 统一处理所有系统异常，提供标准化的错误响应格式
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ExceptionMonitorUtil exceptionMonitorUtil;

    // ==================== 业务异常处理 ====================
    
    @ExceptionHandler(BusinessException.class)
    public ResponseResult<ErrorDetail> handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.warn("业务异常: {}", e.getMessage());
        
        // 记录异常监控
        exceptionMonitorUtil.recordException(e.getClass().getSimpleName(), request.getRequestURI());
        
        // 构建标准错误响应
        ErrorDetail errorDetail = ErrorDetail.of(e, request);
        return ResponseResult.fail(errorDetail.getCode(), errorDetail.getMessage(), errorDetail);
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseResult<ErrorDetail> handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
        log.warn("资源未找到: {}", e.getMessage());
        
        exceptionMonitorUtil.recordException(e.getClass().getSimpleName(), request.getRequestURI());
        
        ErrorDetail errorDetail = ErrorDetail.of(e, request);
        return ResponseResult.fail(errorDetail.getCode(), errorDetail.getMessage(), errorDetail);
    }
    
    @ExceptionHandler(ParameterValidationException.class)
    public ResponseResult<ErrorDetail> handleParameterValidationException(ParameterValidationException e, HttpServletRequest request) {
        log.warn("参数验证异常: {}", e.getMessage());
        
        exceptionMonitorUtil.recordException(e.getClass().getSimpleName(), request.getRequestURI());
        
        ErrorDetail errorDetail = ErrorDetail.of(e, request);
        return ResponseResult.fail(errorDetail.getCode(), errorDetail.getMessage(), errorDetail);
    }
    
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseResult<ErrorDetail> handleUnauthorizedException(UnauthorizedException e, HttpServletRequest request) {
        log.warn("未授权访问: {}", e.getMessage());
        
        exceptionMonitorUtil.recordException(e.getClass().getSimpleName(), request.getRequestURI());
        
        ErrorDetail errorDetail = ErrorDetail.of(e, request);
        return ResponseResult.fail(errorDetail.getCode(), errorDetail.getMessage(), errorDetail);
    }
    
    @ExceptionHandler(ForbiddenException.class)
    public ResponseResult<ErrorDetail> handleForbiddenException(ForbiddenException e, HttpServletRequest request) {
        log.warn("禁止访问: {}", e.getMessage());
        
        exceptionMonitorUtil.recordException(e.getClass().getSimpleName(), request.getRequestURI());
        
        ErrorDetail errorDetail = ErrorDetail.of(e, request);
        return ResponseResult.fail(errorDetail.getCode(), errorDetail.getMessage(), errorDetail);
    }

    // ==================== 参数验证异常处理 ====================
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult<ErrorDetail> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.warn("方法参数验证失败: {}", message);
        
        exceptionMonitorUtil.recordException(e.getClass().getSimpleName(), request.getRequestURI());
        
        ErrorDetail errorDetail = ErrorDetail.of(ResponseCode.INVALID_PARAMETER, request);
        errorDetail.setMessage("参数验证失败: " + message);
        return ResponseResult.fail(errorDetail.getCode(), errorDetail.getMessage(), errorDetail);
    }
    
    @ExceptionHandler(BindException.class)
    public ResponseResult<ErrorDetail> handleBindException(BindException e, HttpServletRequest request) {
        String message = e.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.warn("参数绑定异常: {}", message);
        
        exceptionMonitorUtil.recordException(e.getClass().getSimpleName(), request.getRequestURI());
        
        ErrorDetail errorDetail = ErrorDetail.of(ResponseCode.INVALID_PARAMETER, request);
        errorDetail.setMessage("参数绑定失败: " + message);
        return ResponseResult.fail(errorDetail.getCode(), errorDetail.getMessage(), errorDetail);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseResult<ErrorDetail> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        String message = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        log.warn("约束验证异常: {}", message);
        
        exceptionMonitorUtil.recordException(e.getClass().getSimpleName(), request.getRequestURI());
        
        ErrorDetail errorDetail = ErrorDetail.of(ResponseCode.INVALID_PARAMETER, request);
        errorDetail.setMessage("约束验证失败: " + message);
        return ResponseResult.fail(errorDetail.getCode(), errorDetail.getMessage(), errorDetail);
    }
    
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseResult<ErrorDetail> handleMissingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest request) {
        log.warn("缺少请求参数: {}", e.getParameterName());
        
        exceptionMonitorUtil.recordException(e.getClass().getSimpleName(), request.getRequestURI());
        
        ErrorDetail errorDetail = ErrorDetail.of(ResponseCode.MISSING_REQUIRED_PARAMETER, request);
        errorDetail.setMessage("缺少必需参数: " + e.getParameterName());
        return ResponseResult.fail(errorDetail.getCode(), errorDetail.getMessage(), errorDetail);
    }
    
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseResult<ErrorDetail> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        log.warn("参数类型不匹配: {}", e.getName());
        
        exceptionMonitorUtil.recordException(e.getClass().getSimpleName(), request.getRequestURI());
        
        ErrorDetail errorDetail = ErrorDetail.of(ResponseCode.INVALID_PARAMETER, request);
        errorDetail.setMessage("参数类型错误: " + e.getName());
        return ResponseResult.fail(errorDetail.getCode(), errorDetail.getMessage(), errorDetail);
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseResult<ErrorDetail> handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        log.warn("HTTP消息不可读: {}", e.getMessage());
        
        exceptionMonitorUtil.recordException(e.getClass().getSimpleName(), request.getRequestURI());
        
        ErrorDetail errorDetail = ErrorDetail.of(ResponseCode.INVALID_PARAMETER, request);
        return ResponseResult.fail(errorDetail.getCode(), errorDetail.getMessage(), errorDetail);
    }

    // ==================== HTTP相关异常处理 ====================
    
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseResult<ErrorDetail> handleNoHandlerFoundException(NoHandlerFoundException e, HttpServletRequest request) {
        log.warn("未找到请求处理器: {}", e.getRequestURL());
        
        exceptionMonitorUtil.recordException(e.getClass().getSimpleName(), request.getRequestURI());
        
        ErrorDetail errorDetail = ErrorDetail.of(ResponseCode.NOT_FOUND, request);
        return ResponseResult.fail(errorDetail.getCode(), errorDetail.getMessage(), errorDetail);
    }
    
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseResult<ErrorDetail> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.warn("不支持的HTTP方法: {}", e.getMethod());
        
        exceptionMonitorUtil.recordException(e.getClass().getSimpleName(), request.getRequestURI());
        
        ErrorDetail errorDetail = ErrorDetail.of(ResponseCode.BAD_REQUEST, request);
        errorDetail.setMessage("不支持的请求方法: " + e.getMethod());
        return ResponseResult.fail(errorDetail.getCode(), errorDetail.getMessage(), errorDetail);
    }
    
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseResult<ErrorDetail> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e, HttpServletRequest request) {
        log.warn("不支持的媒体类型: {}", e.getContentType());
        
        exceptionMonitorUtil.recordException(e.getClass().getSimpleName(), request.getRequestURI());
        
        ErrorDetail errorDetail = ErrorDetail.of(ResponseCode.FILE_FORMAT_NOT_SUPPORTED, request);
        return ResponseResult.fail(errorDetail.getCode(), errorDetail.getMessage(), errorDetail);
    }

    // ==================== 文件相关异常处理 ====================
    
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseResult<ErrorDetail> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e, HttpServletRequest request) {
        log.warn("文件上传大小超限: {}", e.getMessage());
        
        exceptionMonitorUtil.recordException(e.getClass().getSimpleName(), request.getRequestURI());
        
        ErrorDetail errorDetail = ErrorDetail.of(ResponseCode.FILE_SIZE_EXCEEDED, request);
        return ResponseResult.fail(errorDetail.getCode(), errorDetail.getMessage(), errorDetail);
    }

    // ==================== 数据库异常处理 ====================
    
    @ExceptionHandler(DataAccessException.class)
    public ResponseResult<ErrorDetail> handleSpringDataAccessException(DataAccessException e, HttpServletRequest request) {
        log.error("数据库访问异常: {}", e.getMessage(), e);
        
        exceptionMonitorUtil.recordException(e.getClass().getSimpleName(), request.getRequestURI());
        ExceptionLogUtil.logException("error", e, request.getRequestURI());
        
        ErrorDetail errorDetail = ErrorDetail.of(ResponseCode.DATABASE_ERROR, request);
        return ResponseResult.fail(errorDetail.getCode(), errorDetail.getMessage(), errorDetail);
    }

    // ==================== 系统异常处理 ====================
    
    @ExceptionHandler(Exception.class)
    public ResponseResult<ErrorDetail> handleException(Exception e, HttpServletRequest request) {
        log.error("系统异常: {}", e.getMessage(), e);
        
        exceptionMonitorUtil.recordException(e.getClass().getSimpleName(), request.getRequestURI());
        ExceptionLogUtil.logException("error", e, request.getRequestURI());
        
        ErrorDetail errorDetail = ErrorDetail.of(ResponseCode.INTERNAL_SERVER_ERROR, request);
        return ResponseResult.fail(errorDetail.getCode(), errorDetail.getMessage(), errorDetail);
    }
    
    /**
     * 统一异常处理方法 - 兜底处理
     */
    @ExceptionHandler(Throwable.class)
    public ResponseResult<ErrorDetail> handleThrowable(Throwable e, HttpServletRequest request) {
        log.error("未知异常: {}", e.getMessage(), e);
        
        exceptionMonitorUtil.recordException(e.getClass().getSimpleName(), request.getRequestURI());
        ExceptionLogUtil.logException("error", new Exception(e), request.getRequestURI());
        
        ErrorDetail errorDetail = ErrorDetail.of(ResponseCode.INTERNAL_SERVER_ERROR, request);
        return ResponseResult.fail(errorDetail.getCode(), errorDetail.getMessage(), errorDetail);
    }
}