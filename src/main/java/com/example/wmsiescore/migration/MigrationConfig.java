package com.example.wmsiescore.migration;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据迁移配置类
 */
@Data
@Schema(description = "数据迁移配置")
public class MigrationConfig implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "源数据库URL", example = "jdbc:mysql://localhost:3306/source_db")
    private String sourceUrl;
    
    @Schema(description = "源数据库用户名", example = "root")
    private String sourceUsername;
    
    @Schema(description = "源数据库密码", example = "password")
    private String sourcePassword;
    
    @Schema(description = "目标数据库URL", example = "jdbc:mysql://localhost:3306/target_db")
    private String targetUrl;
    
    @Schema(description = "目标数据库用户名", example = "root")
    private String targetUsername;
    
    @Schema(description = "目标数据库密码", example = "password")
    private String targetPassword;
    
    @Schema(description = "批量处理大小", example = "1000")
    private int batchSize = 1000;
    
    @Schema(description = "是否启用断点续传", example = "true")
    private boolean enableResume = true;
    
    @Schema(description = "迁移进度存储路径", example = "/tmp/migration/progress")
    private String progressPath = "/tmp/migration/progress";
    
    @Schema(description = "迁移报告存储路径", example = "/tmp/migration/report")
    private String reportPath = "/tmp/migration/report";
    
    @Schema(description = "是否启用数据校验", example = "true")
    private boolean enableValidation = true;
    
    @Schema(description = "校验抽样比例(0-1)", example = "0.1")
    private double validationSampleRatio = 0.1;
}
