package com.example.wmsiescore.aspect;

import com.alibaba.fastjson.JSON;
import com.example.wmsiescore.annotation.NoLog;
import com.example.wmsiescore.common.ResponseResult;
import com.example.wmsiescore.config.LogAspectConfig;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Controller层日志拦截器
 * 用于监控所有Controller层的接口请求，记录请求参数和返回结果
 */
@Slf4j
@Aspect
@Component
public class ControllerLogAspect {
    
    @Autowired
    private LogAspectConfig logAspectConfig;

    /**
     * 定义切点：拦截所有Controller类的公共方法
     */
    @Pointcut("execution(public * com.example.wmsiescore.controller..*.*(..))")
    public void controllerPointcut() {
    }

    /**
     * 环绕通知：记录方法调用的开始和结束，以及参数和返回值
     *
     * @param joinPoint 连接点
     * @return 方法返回值
     * @throws Throwable 可能抛出的异常
     */
    @Around("controllerPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 检查是否启用日志拦截
        if (!logAspectConfig.isEnabled()) {
            return joinPoint.proceed();
        }
        
        // 检查方法是否有@NoLog注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        NoLog noLog = method.getAnnotation(NoLog.class);
        if (noLog != null) {
            // 如果有@NoLog注解，直接执行方法，不记录日志
            return joinPoint.proceed();
        }
        
        // 生成唯一请求ID，用于关联请求和响应日志
        String requestId = logAspectConfig.isLogRequestId() ? 
                UUID.randomUUID().toString().replace("-", "") : "";
        
        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
        
        // 获取方法信息
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();
        
        // 记录请求开始时间
        long startTime = System.currentTimeMillis();
        
        // 获取请求参数
        Object[] args = joinPoint.getArgs();
        List<Object> filteredArgs = logAspectConfig.isLogRequestArgs() ? filterArgs(args) : new ArrayList<>();
        
        // 构建请求日志
        StringBuilder requestLog = new StringBuilder();
        if (logAspectConfig.isLogRequestId()) {
            requestLog.append("请求开始 [").append(requestId).append("] - ");
        }
        requestLog.append("URI: ").append(request != null ? request.getRequestURI() : "N/A")
                  .append(" - Method: ").append(className).append(".").append(methodName);
        
        if (logAspectConfig.isLogRequestArgs()) {
            requestLog.append(" - Args: ").append(JSON.toJSONString(filteredArgs));
        }
        
        // 记录请求日志
        log.info(requestLog.toString());
        
        Object result = null;
        Exception exception = null;
        
        try {
            // 执行目标方法
            result = joinPoint.proceed();
            
            // 计算方法执行时间
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            // 构建响应日志
            StringBuilder responseLog = new StringBuilder();
            if (logAspectConfig.isLogRequestId()) {
                responseLog.append("请求结束 [").append(requestId).append("] - ");
            }
            responseLog.append("Method: ").append(className).append(".").append(methodName);
            
            if (logAspectConfig.isLogExecutionTime()) {
                responseLog.append(" - ExecutionTime: ").append(executionTime).append("ms");
            }
            
            if (logAspectConfig.isLogResponseResult()) {
                // 过滤返回结果，避免记录敏感信息或过大的响应体
                Object filteredResult = filterResult(result);
                responseLog.append(" - Result: ").append(JSON.toJSONString(filteredResult));
            }
            
            // 记录响应日志
            log.info(responseLog.toString());
            
            return result;
        } catch (Exception e) {
            // 记录异常信息
            exception = e;
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            StringBuilder errorLog = new StringBuilder();
            if (logAspectConfig.isLogRequestId()) {
                errorLog.append("请求异常 [").append(requestId).append("] - ");
            }
            errorLog.append("Method: ").append(className).append(".").append(methodName);
            
            if (logAspectConfig.isLogExecutionTime()) {
                errorLog.append(" - ExecutionTime: ").append(executionTime).append("ms");
            }
            
            errorLog.append(" - Exception: ").append(e.getMessage());
            
            if (logAspectConfig.isLogExceptionStackTrace()) {
                log.error(errorLog.toString(), e);
            } else {
                log.error(errorLog.toString());
            }
            
            throw e;
        }
    }
    
    /**
     * 过滤请求参数，避免记录敏感信息或过大的参数
     *
     * @param args 原始参数数组
     * @return 过滤后的参数列表
     */
    private List<Object> filterArgs(Object[] args) {
        List<Object> filteredArgs = new ArrayList<>();
        
        if (args == null || args.length == 0) {
            return filteredArgs;
        }
        
        for (Object arg : args) {
            // 过滤掉不需要记录的参数类型
            if (arg == null || 
                arg instanceof HttpServletRequest || 
                arg instanceof HttpServletResponse || 
                arg instanceof MultipartFile) {
                filteredArgs.add(arg != null ? arg.getClass().getSimpleName() + "[FILTERED]" : null);
                continue;
            }
            
            // 对于字符串类型，限制长度
            if (arg instanceof String) {
                String strArg = (String) arg;
                int maxLength = logAspectConfig.getMaxArgLength();
                if (strArg.length() > maxLength) {
                    filteredArgs.add(strArg.substring(0, maxLength) + "...[TRUNCATED]");
                } else {
                    filteredArgs.add(strArg);
                }
                continue;
            }
            
            // 对于字节数组类型，记录大小而不是内容
            if (arg instanceof byte[]) {
                filteredArgs.add("byte[" + ((byte[]) arg).length + "]");
                continue;
            }
            
            // 对于其他类型，直接添加
            filteredArgs.add(arg);
        }
        
        return filteredArgs;
    }
    
    /**
     * 过滤返回结果，避免记录敏感信息或过大的响应体
     *
     * @param result 原始返回结果
     * @return 过滤后的返回结果
     */
    private Object filterResult(Object result) {
        if (result == null) {
            return null;
        }
        
        // 对于ResponseResult类型，只记录code和message，不记录data
        if (result instanceof ResponseResult) {
            ResponseResult<?> responseResult = (ResponseResult<?>) result;
            ResponseResult<Object> filteredResult = new ResponseResult<>();
            filteredResult.setCode(responseResult.getCode());
            filteredResult.setMessage(responseResult.getMessage());
            filteredResult.setTimestamp(responseResult.getTimestamp());
            
            // 对于data部分，如果太大则只记录类型
            Object data = responseResult.getData();
            if (data != null) {
                String dataStr = JSON.toJSONString(data);
                int maxLength = logAspectConfig.getMaxResultLength();
                if (dataStr.length() > maxLength) {
                    filteredResult.setData(data.getClass().getSimpleName() + "[LARGE_DATA]");
                } else {
                    filteredResult.setData(data);
                }
            }
            
            return filteredResult;
        }
        
        // 对于字符串类型，限制长度
        if (result instanceof String) {
            String strResult = (String) result;
            int maxLength = logAspectConfig.getMaxResultLength();
            if (strResult.length() > maxLength) {
                return strResult.substring(0, maxLength) + "...[TRUNCATED]";
            }
            return result;
        }
        
        // 对于字节数组类型，记录大小而不是内容
        if (result instanceof byte[]) {
            return "byte[" + ((byte[]) result).length + "]";
        }
        
        // 对于其他类型，如果序列化后太大则只记录类型
        try {
            String resultStr = JSON.toJSONString(result);
            int maxLength = logAspectConfig.getMaxResultLength();
            if (resultStr.length() > maxLength) {
                return result.getClass().getSimpleName() + "[LARGE_DATA]";
            }
            return result;
        } catch (Exception e) {
            return result.getClass().getSimpleName() + "[SERIALIZATION_ERROR]";
        }
    }
}