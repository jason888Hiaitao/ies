package com.example.wmsiescore.config;

import com.example.wmsiescore.util.ExceptionMonitorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * 异常处理配置类
 */
@Slf4j
@Configuration
public class ExceptionHandlingConfig {

    @Autowired
    private ExceptionMonitorUtil exceptionMonitorUtil;

    /**
     * 自定义错误属性
     */
    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
                Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
                
                // 移除敏感信息
                errorAttributes.remove("exception");
                errorAttributes.remove("trace");
                
                // 添加自定义信息
                errorAttributes.put("timestamp", System.currentTimeMillis());
                errorAttributes.put("status", errorAttributes.get("status"));
                errorAttributes.put("error", errorAttributes.get("error"));
                errorAttributes.put("message", errorAttributes.get("message"));
                errorAttributes.put("path", errorAttributes.get("path"));
                
                return errorAttributes;
            }
        };
    }

    /**
     * 异常处理通知器
     */
    @Bean
    public ExceptionHandlingNotifier exceptionHandlingNotifier() {
        return new ExceptionHandlingNotifier(exceptionMonitorUtil);
    }

    /**
     * 异常处理通知器实现
     */
    public static class ExceptionHandlingNotifier {
        private final ExceptionMonitorUtil exceptionMonitorUtil;

        public ExceptionHandlingNotifier(ExceptionMonitorUtil exceptionMonitorUtil) {
            this.exceptionMonitorUtil = exceptionMonitorUtil;
        }

        public void notifyException(String exceptionType, String operation) {
            exceptionMonitorUtil.recordException(exceptionType, operation);
        }
    }
}