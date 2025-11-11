package com.example.wmsiescore.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常日志记录工具类
 */
@Slf4j
public class ExceptionLogUtil {

    /**
     * 记录异常信息，包含请求上下文
     */
    public static void logException(String level, Exception e, String operation) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                
                Map<String, Object> requestInfo = new HashMap<>();
                requestInfo.put("url", request.getRequestURL().toString());
                requestInfo.put("method", request.getMethod());
                requestInfo.put("ip", getClientIpAddress(request));
                requestInfo.put("userAgent", request.getHeader("User-Agent"));
                requestInfo.put("parameters", getRequestParameters(request));
                requestInfo.put("operation", operation);
                
                String logMessage = String.format("异常发生 - 请求信息: %s, 异常信息: %s", 
                        requestInfo, e.getMessage());
                
                switch (level.toLowerCase()) {
                    case "error":
                        log.error(logMessage, e);
                        break;
                    case "warn":
                        log.warn(logMessage, e);
                        break;
                    default:
                        log.info(logMessage, e);
                }
            } else {
                log.error("异常发生 - 操作: {}, 异常信息: {}", operation, e.getMessage(), e);
            }
        } catch (Exception ex) {
            log.error("记录异常日志时发生错误: {}", ex.getMessage(), ex);
        }
    }

    /**
     * 获取客户端IP地址
     */
    private static String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty() && !"unknown".equalsIgnoreCase(xRealIp)) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }

    /**
     * 获取请求参数
     */
    private static Map<String, String> getRequestParameters(HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            // 敏感参数脱敏
            if (isSensitiveParameter(paramName)) {
                paramValue = "***";
            }
            parameters.put(paramName, paramValue);
        }
        
        return parameters;
    }

    /**
     * 判断是否为敏感参数
     */
    private static boolean isSensitiveParameter(String paramName) {
        String lowerParamName = paramName.toLowerCase();
        return lowerParamName.contains("password") || 
               lowerParamName.contains("token") || 
               lowerParamName.contains("secret") ||
               lowerParamName.contains("key");
    }
}