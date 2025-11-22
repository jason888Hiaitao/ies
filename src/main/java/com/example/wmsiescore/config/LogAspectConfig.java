package com.example.wmsiescore.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * AOP日志拦截器配置类
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "log.aspect")
public class LogAspectConfig {
    
    /**
     * 是否启用Controller层日志拦截
     */
    private boolean enabled = true;
    
    /**
     * 是否记录请求参数
     */
    private boolean logRequestArgs = true;
    
    /**
     * 是否记录返回结果
     */
    private boolean logResponseResult = true;
    
    /**
     * 是否记录方法执行时间
     */
    private boolean logExecutionTime = true;
    
    /**
     * 请求参数最大记录长度
     */
    private int maxArgLength = 200;
    
    /**
     * 返回结果最大记录长度
     */
    private int maxResultLength = 1000;
    
    /**
     * 是否记录异常堆栈信息
     */
    private boolean logExceptionStackTrace = true;
    
    /**
     * 是否记录请求ID
     */
    private boolean logRequestId = true;
}