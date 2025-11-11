package com.example.wmsiescore.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 异常处理配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "exception")
public class ExceptionHandlingProperties {

    /**
     * 监控配置
     */
    private MonitorConfig monitor = new MonitorConfig();

    /**
     * 日志配置
     */
    private LoggingConfig logging = new LoggingConfig();

    /**
     * 响应配置
     */
    private ResponseConfig response = new ResponseConfig();

    /**
     * 告警配置
     */
    private AlertConfig alert = new AlertConfig();

    @Data
    public static class MonitorConfig {
        /**
         * 是否启用监控
         */
        private boolean enabled = true;
        
        /**
         * 告警阈值
         */
        private int alertThreshold = 10;
        
        /**
         * 时间窗口（分钟）
         */
        private int timeWindowMinutes = 5;
        
        /**
         * 告警冷却时间（分钟）
         */
        private int alertCooldownMinutes = 30;
    }

    @Data
    public static class LoggingConfig {
        /**
         * 是否记录请求参数
         */
        private boolean includeParameters = true;
        
        /**
         * 是否脱敏敏感参数
         */
        private boolean maskSensitiveParams = true;
        
        /**
         * 敏感参数关键词
         */
        private List<String> sensitiveKeywords = java.util.Arrays.asList("password", "token", "secret", "key", "credential");
        
        /**
         * 异常堆栈记录的最大行数
         */
        private int maxStackTraceLines = 50;
    }

    @Data
    public static class ResponseConfig {
        /**
         * 是否在响应中包含异常详情
         */
        private boolean includeExceptionDetails = false;
        
        /**
         * 是否在响应中包含请求ID
         */
        private boolean includeRequestId = true;
        
        /**
         * 自定义错误页面
         */
        private Map<Integer, String> errorPages;
    }

    @Data
    public static class AlertConfig {
        /**
         * 邮件告警配置
         */
        private EmailConfig email = new EmailConfig();
        
        /**
         * 短信告警配置
         */
        private SmsConfig sms = new SmsConfig();
        
        /**
         * 钉钉告警配置
         */
        private DingTalkConfig dingtalk = new DingTalkConfig();
        
        /**
         * 企业微信告警配置
         */
        private WechatConfig wechat = new WechatConfig();
    }

    @Data
    public static class EmailConfig {
        private boolean enabled = false;
        private String smtpHost;
        private int smtpPort = 587;
        private String username;
        private String password;
        private List<String> recipients;
    }

    @Data
    public static class SmsConfig {
        private boolean enabled = false;
        private String provider;
        private String accessKey;
        private String secretKey;
        private List<String> phoneNumbers;
    }

    @Data
    public static class DingTalkConfig {
        private boolean enabled = false;
        private String webhookUrl;
        private String secret;
    }

    @Data
    public static class WechatConfig {
        private boolean enabled = false;
        private String webhookUrl;
    }
}