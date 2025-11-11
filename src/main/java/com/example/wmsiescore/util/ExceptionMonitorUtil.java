package com.example.wmsiescore.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * 异常监控和告警工具类
 */
@Slf4j
@Component
public class ExceptionMonitorUtil {

    @Autowired(required = false)
    private StringRedisTemplate redisTemplate;

    private static final String EXCEPTION_COUNT_PREFIX = "exception:count:";
    private static final String EXCEPTION_ALERT_PREFIX = "exception:alert:";
    private static final int ALERT_THRESHOLD = 10; // 10次异常触发告警
    private static final int ALERT_WINDOW_MINUTES = 5; // 5分钟时间窗口

    /**
     * 记录异常发生次数
     */
    public void recordException(String exceptionType, String operation) {
        try {
            if (redisTemplate != null) {
                String key = EXCEPTION_COUNT_PREFIX + exceptionType + ":" + operation;
                Long count = redisTemplate.opsForValue().increment(key);
                
                // 设置过期时间
                if (count != null && count == 1) {
                    redisTemplate.expire(key, ALERT_WINDOW_MINUTES, TimeUnit.MINUTES);
                }
                
                // 检查是否需要告警
                if (count != null && count >= ALERT_THRESHOLD) {
                    sendAlert(exceptionType, operation, count);
                    // 重置计数器
                    redisTemplate.delete(key);
                }
            }
        } catch (Exception e) {
            log.error("记录异常监控信息失败: {}", e.getMessage(), e);
        }
    }

    /**
     * 发送告警
     */
    private void sendAlert(String exceptionType, String operation, Long count) {
        String alertMessage = String.format(
            "【系统告警】异常类型: %s, 操作: %s, 在%d分钟内发生%d次异常, 时间: %s",
            exceptionType, operation, ALERT_WINDOW_MINUTES, count,
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        
        log.error(alertMessage);
        
        // 这里可以扩展其他告警方式，如邮件、短信、钉钉等
        // sendEmail(alertMessage);
        // sendSms(alertMessage);
        // sendDingTalk(alertMessage);
        
        // 记录告警信息
        recordAlert(exceptionType, operation, count, alertMessage);
    }

    /**
     * 记录告警信息
     */
    private void recordAlert(String exceptionType, String operation, Long count, String message) {
        try {
            if (redisTemplate != null) {
                String key = EXCEPTION_ALERT_PREFIX + System.currentTimeMillis();
                String alertInfo = String.format("%s|%s|%d|%s", 
                    exceptionType, operation, count, message);
                redisTemplate.opsForValue().set(key, alertInfo, 24, TimeUnit.HOURS);
            }
        } catch (Exception e) {
            log.error("记录告警信息失败: {}", e.getMessage(), e);
        }
    }

    /**
     * 检查系统健康状态
     */
    public boolean isSystemHealthy() {
        try {
            if (redisTemplate != null) {
                // 检查最近的异常数量
                Long recentExceptions = redisTemplate.keys(EXCEPTION_COUNT_PREFIX + "*")
                    .stream()
                    .mapToLong(key -> {
                        String count = redisTemplate.opsForValue().get(key);
                        return count != null ? Long.parseLong(count) : 0;
                    })
                    .sum();
                
                // 如果最近异常总数超过阈值，认为系统不健康
                return recentExceptions < ALERT_THRESHOLD * 2;
            }
        } catch (Exception e) {
            log.error("检查系统健康状态失败: {}", e.getMessage(), e);
        }
        return true; // 默认认为健康
    }

    /**
     * 获取异常统计信息
     */
    public String getExceptionStatistics() {
        try {
            if (redisTemplate != null) {
                StringBuilder stats = new StringBuilder();
                redisTemplate.keys(EXCEPTION_COUNT_PREFIX + "*")
                    .forEach(key -> {
                        String count = redisTemplate.opsForValue().get(key);
                        if (count != null) {
                            String[] parts = key.split(":");
                            if (parts.length >= 3) {
                                stats.append(String.format("异常类型: %s, 操作: %s, 次数: %s\n", 
                                    parts[2], parts[3], count));
                            }
                        }
                    });
                return stats.toString();
            }
        } catch (Exception e) {
            log.error("获取异常统计信息失败: {}", e.getMessage(), e);
        }
        return "暂无统计数据";
    }
}