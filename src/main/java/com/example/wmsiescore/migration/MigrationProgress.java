package com.example.wmsiescore.migration;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据迁移进度类
 */
@Data
@Schema(description = "数据迁移进度")
public class MigrationProgress implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "表名")
    private String tableName;
    
    @Schema(description = "总记录数")
    private long totalRecords;
    
    @Schema(description = "已迁移记录数")
    private long migratedRecords;
    
    @Schema(description = "迁移状态", example = "RUNNING, COMPLETED, FAILED, PAUSED")
    private String status;
    
    @Schema(description = "开始时间")
    private LocalDateTime startTime;
    
    @Schema(description = "结束时间")
    private LocalDateTime endTime;
    
    @Schema(description = "错误信息")
    private String errorMessage;
    
    @Schema(description = "最后迁移的主键值（用于断点续传）")
    private Object lastMigratedKey;
    
    @Schema(description = "是否创建了新表")
    private boolean tableCreated;
    
    @Schema(description = "迁移进度百分比")
    public double getProgressPercentage() {
        if (totalRecords == 0) {
            return 0.0;
        }
        return (double) migratedRecords / totalRecords * 100;
    }
    
    @Schema(description = "是否已完成")
    public boolean isCompleted() {
        return "COMPLETED".equals(status);
    }
    
    @Schema(description = "是否失败")
    public boolean isFailed() {
        return "FAILED".equals(status);
    }
    
    @Schema(description = "是否正在运行")
    public boolean isRunning() {
        return "RUNNING".equals(status);
    }
}
