package com.example.wmsiescore.common;

import com.example.wmsiescore.exception.*;
import lombok.extern.slf4j.Slf4j;
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

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ==================== 业务异常处理 ====================
    
    @ExceptionHandler(BusinessException.class)
    public ResponseResult<String> handleBusinessException(BusinessException e) {
        log.warn("业务异常: {}", e.getMessage());
        return ResponseResult.error(e.getCode(), e.getMessage());
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseResult<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        log.warn("资源未找到: {}", e.getMessage());
        return ResponseResult.error(e.getCode(), e.getMessage());
    }
    
    @ExceptionHandler(ParameterValidationException.class)
    public ResponseResult<String> handleParameterValidationException(ParameterValidationException e) {
        log.warn("参数验证异常: {}", e.getMessage());
        return ResponseResult.error(e.getCode(), e.getMessage());
    }
    
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseResult<String> handleUnauthorizedException(UnauthorizedException e) {
        log.warn("未授权访问: {}", e.getMessage());
        return ResponseResult.error(e.getCode(), e.getMessage());
    }
    
    @ExceptionHandler(ForbiddenException.class)
    public ResponseResult<String> handleForbiddenException(ForbiddenException e) {
        log.warn("禁止访问: {}", e.getMessage());
        return ResponseResult.error(e.getCode(), e.getMessage());
    }
    

    // ==================== 参数验证异常处理 ====================
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.warn("方法参数验证失败: {}", message);
        return ResponseResult.error(ResponseCode.INVALID_PARAMETER.getCode(), "参数验证失败: " + message);
    }
    
    @ExceptionHandler(BindException.class)
    public ResponseResult<String> handleBindException(BindException e) {
        String message = e.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.warn("参数绑定异常: {}", message);
        return ResponseResult.error(ResponseCode.INVALID_PARAMETER.getCode(), "参数绑定失败: " + message);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseResult<String> handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        log.warn("约束验证异常: {}", message);
        return ResponseResult.error(ResponseCode.INVALID_PARAMETER.getCode(), "约束验证失败: " + message);
    }
    
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseResult<String> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.warn("缺少请求参数: {}", e.getParameterName());
        return ResponseResult.error(ResponseCode.MISSING_REQUIRED_PARAMETER.getCode(), 
                "缺少必需参数: " + e.getParameterName());
    }
    
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseResult<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.warn("参数类型不匹配: {}", e.getName());
        return ResponseResult.error(ResponseCode.INVALID_PARAMETER.getCode(), 
                "参数类型错误: " + e.getName());
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseResult<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn("HTTP消息不可读: {}", e.getMessage());
        return ResponseResult.error(ResponseCode.INVALID_PARAMETER.getCode(), "请求体格式错误");
    }

    // ==================== HTTP相关异常处理 ====================
    
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseResult<String> handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.warn("未找到请求处理器: {}", e.getRequestURL());
        return ResponseResult.error(ResponseCode.NOT_FOUND.getCode(), "请求的资源不存在");
    }
    
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseResult<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.warn("不支持的HTTP方法: {}", e.getMethod());
        return ResponseResult.error(ResponseCode.BAD_REQUEST.getCode(), 
                "不支持的请求方法: " + e.getMethod());
    }
    
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseResult<String> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.warn("不支持的媒体类型: {}", e.getContentType());
        return ResponseResult.error(ResponseCode.FILE_FORMAT_NOT_SUPPORTED.getCode(), 
                "不支持的媒体类型");
    }

    // ==================== 文件相关异常处理 ====================
    
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseResult<String> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.warn("文件上传大小超限: {}", e.getMessage());
        return ResponseResult.error(ResponseCode.FILE_SIZE_EXCEEDED.getCode(), "文件大小超出限制");
    }

    // ==================== 数据库异常处理 ====================
    
    @ExceptionHandler(DataAccessException.class)
    public ResponseResult<String> handleSpringDataAccessException(DataAccessException e) {
        log.error("数据库访问异常: {}", e.getMessage(), e);
        return ResponseResult.error(ResponseCode.DATABASE_ERROR.getCode(), "数据库操作失败");
    }

    // ==================== 系统异常处理 ====================
    
    @ExceptionHandler(Exception.class)
    public ResponseResult<String> handleException(Exception e) {
        log.error("系统异常: {}", e.getMessage(), e);
        return ResponseResult.error(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), "系统内部错误");
    }
}