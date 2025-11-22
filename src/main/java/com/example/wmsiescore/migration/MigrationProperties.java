package com.example.wmsiescore.migration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 数据迁移配置属性类
 */
@Data
@Component
@ConfigurationProperties(prefix = "migration")
public class MigrationProperties {
    
    /**
     * 源数据库配置
     */
    private Database source = new Database();
    
    /**
     * 目标数据库配置
     */
    private Database target = new Database();
    
    /**
     * 批量处理大小
     */
    private int batchSize = 1000;
    
    /**
     * 是否启用断点续传
     */
    private boolean enableResume = true;
    
    /**
     * 是否启用数据校验
     */
    private boolean enableValidation = true;
    
    /**
     * 校验抽样比例
     */
    private double validationSampleRatio = 0.1;
    
    /**
     * 迁移进度存储路径
     */
    private String progressPath = "/tmp/migration/progress";
    
    /**
     * 迁移报告存储路径
     */
    private String reportPath = "/tmp/migration/report";
    
    /**
     * 数据库配置内部类
     */
    @Data
    public static class Database {
        private String url;
        private String username;
        private String password;
    }
}