package com.example.wmsiescore.aspect;

import com.example.wmsiescore.config.ExceptionHandlingProperties;
import com.example.wmsiescore.exception.BusinessException;
import com.example.wmsiescore.util.ExceptionLogUtil;
import com.example.wmsiescore.util.ExceptionMonitorUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 异常处理切面
 */
@Slf4j
@Aspect
@Component
public class ExceptionHandlingAspect {

    @Autowired
    private ExceptionMonitorUtil exceptionMonitorUtil;

    @Autowired
    private ExceptionHandlingProperties properties;

    /**
     * 环绕通知 - 拦截Service层方法
     */
    @Around("execution(* com.example.wmsiescore.service.impl.*.*(..))")
    public Object handleServiceException(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String operation = className + "." + methodName;

        try {
            // 记录方法开始执行
            if (log.isDebugEnabled()) {
                log.debug("开始执行方法: {}", operation);
            }

            // 执行目标方法
            Object result = joinPoint.proceed();

            // 记录方法执行成功
            if (log.isDebugEnabled()) {
                log.debug("方法执行成功: {}", operation);
            }

            return result;

        } catch (BusinessException e) {
            // 业务异常处理
            handleBusinessException(e, operation);
            throw e;

        } catch (Exception e) {
            // 系统异常处理
            handleSystemException(e, operation);
            throw e;
        }
    }

    /**
     * 环绕通知 - 拦截Controller层方法
     */
    @Around("execution(* com.example.wmsiescore.controller.*.*(..))")
    public Object handleControllerException(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String operation = "Controller." + className + "." + methodName;

        long startTime = System.currentTimeMillis();

        try {
            // 执行目标方法
            Object result = joinPoint.proceed();

            // 记录执行时间
            long executionTime = System.currentTimeMillis() - startTime;
            if (executionTime > 1000) { // 超过1秒记录警告
                log.warn("方法执行耗时较长: {}ms, 方法: {}", executionTime, operation);
            }

            return result;

        } catch (BusinessException e) {
            // 业务异常处理
            handleBusinessException(e, operation);
            throw e;

        } catch (Exception e) {
            // 系统异常处理
            handleSystemException(e, operation);
            throw e;
        }
    }

    /**
     * 处理业务异常
     */
    private void handleBusinessException(BusinessException e, String operation) {
        // 记录异常日志
        ExceptionLogUtil.logException("warn", e, operation);

        // 记录异常监控
        if (properties.getMonitor().isEnabled()) {
            exceptionMonitorUtil.recordException(e.getClass().getSimpleName(), operation);
        }

        // 记录业务异常
        log.warn("业务异常 - 操作: {}, 异常类型: {}, 错误信息: {}", 
                operation, e.getClass().getSimpleName(), e.getMessage());
    }

    /**
     * 处理系统异常
     */
    private void handleSystemException(Exception e, String operation) {
        // 记录异常日志
        ExceptionLogUtil.logException("error", e, operation);

        // 记录异常监控
        if (properties.getMonitor().isEnabled()) {
            exceptionMonitorUtil.recordException(e.getClass().getSimpleName(), operation);
        }

        // 记录系统异常
        log.error("系统异常 - 操作: {}, 异常类型: {}, 错误信息: {}", 
                operation, e.getClass().getSimpleName(), e.getMessage(), e);
    }
}