package com.example.wmsiescore.migration;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据迁移报告类
 */
@Data
@Schema(description = "数据迁移报告")
public class MigrationReport implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "迁移开始时间")
    private LocalDateTime startTime;
    
    @Schema(description = "迁移结束时间")
    private LocalDateTime endTime;
    
    @Schema(description = "总表数")
    private int totalTables;
    
    @Schema(description = "成功迁移的表数")
    private int successTables;
    
    @Schema(description = "失败的表数")
    private int failedTables;
    
    @Schema(description = "总记录数")
    private long totalRecords;
    
    @Schema(description = "成功迁移的记录数")
    private long successRecords;
    
    @Schema(description = "失败的记录数")
    private long failedRecords;
    
    @Schema(description = "表迁移详情")
    private Map<String, TableMigrationResult> tableResults = new HashMap<>();
    
    @Schema(description = "错误日志")
    private List<String> errorLogs = new ArrayList<>();
    
    @Schema(description = "警告日志")
    private List<String> warningLogs = new ArrayList<>();
    
    @Schema(description = "信息日志")
    private List<String> infoLogs = new ArrayList<>();
    
    @Schema(description = "迁移总耗时（秒）")
    public long getTotalDurationInSeconds() {
        if (startTime == null || endTime == null) {
            return 0;
        }
        return java.time.Duration.between(startTime, endTime).getSeconds();
    }
    
    @Schema(description = "迁移成功率")
    public double getSuccessRate() {
        if (totalTables == 0) {
            return 0.0;
        }
        return (double) successTables / totalTables * 100;
    }
    
    /**
     * 表迁移结果内部类
     */
    @Data
    @Schema(description = "表迁移结果")
    public static class TableMigrationResult implements Serializable {
        
        private static final long serialVersionUID = 1L;
        
        @Schema(description = "表名")
        private String tableName;
        
        @Schema(description = "迁移状态")
        private String status;
        
        @Schema(description = "总记录数")
        private long totalRecords;
        
        @Schema(description = "成功迁移记录数")
        private long successRecords;
        
        @Schema(description = "失败记录数")
        private long failedRecords;
        
        @Schema(description = "是否创建了新表")
        private boolean tableCreated;
        
        @Schema(description = "字段映射信息")
        private Map<String, String> columnMapping = new HashMap<>();
        
        @Schema(description = "错误信息")
        private String errorMessage;
        
        @Schema(description = "迁移耗时（秒）")
        private long durationInSeconds;
    }
}
