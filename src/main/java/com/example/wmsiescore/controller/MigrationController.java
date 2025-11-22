package com.example.wmsiescore.controller;

import com.example.wmsiescore.common.ResponseResult;
import com.example.wmsiescore.migration.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据迁移控制器 - 重构版本
 */
@Slf4j
@RestController
@RequestMapping("/api/migration")
@Tag(name = "数据迁移管理", description = "数据迁移相关操作")
@Validated
public class MigrationController {
    
    @Autowired
    private MigrationService migrationService;
    
    @Autowired
    private MigrationProperties migrationProperties;
    
    /**
     * 统一迁移接口入口
     * 支持多种操作类型：MIGRATE, PAUSE, RESUME, CANCEL, VALIDATE, REPORT, PROGRESS
     */
    @PostMapping("/execute")
    @Operation(summary = "统一迁移接口", description = "支持多种迁移操作的统一入口")
    public ResponseResult<Object> executeMigration(
            @Parameter(description = "迁移请求", required = true) @Valid @RequestBody MigrationRequest request) {
        
        try {
            // 参数校验
            validateRequest(request);
            
            // 构建迁移配置
            MigrationConfig config = buildMigrationConfig(request);
            
            // 根据操作类型执行相应操作
            String operation = request.getOperation().toUpperCase();
            List<String> tableNames = request.getTableNames();
            
            switch (operation) {
                case "MIGRATE":
                    if (tableNames.size() == 1) {
                        // 单表迁移
                        MigrationProgress progress = migrationService.migrateTable(config, tableNames.get(0));
                        return ResponseResult.success("迁移任务已启动", progress);
                    } else {
                        // 多表迁移
                        MigrationReport report = migrationService.migrateTables(config, tableNames);
                        return ResponseResult.success("批量迁移任务已启动", report);
                    }
                
                case "PAUSE":
                    Map<String, Boolean> pauseResults = new HashMap<>();
                    for (String tableName : tableNames) {
                        boolean result = migrationService.pauseMigration(config, tableName);
                        pauseResults.put(tableName, result);
                    }
                    return ResponseResult.success("暂停操作完成", pauseResults);
                
                case "RESUME":
                    if (tableNames.size() == 1) {
                        // 单表恢复
                        MigrationProgress progress = migrationService.resumeMigration(config, tableNames.get(0));
                        return ResponseResult.success("恢复任务已启动", progress);
                    } else {
                        // 多表恢复
                        Map<String, MigrationProgress> resumeResults = new HashMap<>();
                        for (String tableName : tableNames) {
                            MigrationProgress progress = migrationService.resumeMigration(config, tableName);
                            resumeResults.put(tableName, progress);
                        }
                        return ResponseResult.success("批量恢复任务已启动", resumeResults);
                    }
                
                case "CANCEL":
                    Map<String, Boolean> cancelResults = new HashMap<>();
                    for (String tableName : tableNames) {
                        boolean result = migrationService.cancelMigration(config, tableName);
                        cancelResults.put(tableName, result);
                    }
                    return ResponseResult.success("取消操作完成", cancelResults);
                
                case "VALIDATE":
                    Map<String, Boolean> validateResults = new HashMap<>();
                    for (String tableName : tableNames) {
                        boolean result = migrationService.validateMigration(config, tableName);
                        validateResults.put(tableName, result);
                    }
                    return ResponseResult.success("验证操作完成", validateResults);
                
                case "REPORT":
                    MigrationReport report = migrationService.generateReport(config, tableNames);
                    return ResponseResult.success("报告生成成功", report);
                
                case "PROGRESS":
                    if (tableNames.size() == 1) {
                        // 单表进度
                        MigrationProgress progress = migrationService.getMigrationProgress(config, tableNames.get(0));
                        return ResponseResult.success("查询成功", progress);
                    } else {
                        // 多表进度
                        Map<String, MigrationProgress> progressResults = new HashMap<>();
                        for (String tableName : tableNames) {
                            MigrationProgress progress = migrationService.getMigrationProgress(config, tableName);
                            progressResults.put(tableName, progress);
                        }
                        return ResponseResult.success("查询成功", progressResults);
                    }
                
                default:
                    throw new IllegalArgumentException("不支持的操作类型: " + operation);
            }
        } catch (IllegalArgumentException e) {
            log.error("参数校验失败: {}", e.getMessage());
            return ResponseResult.fail(e.getMessage());
        } catch (Exception e) {
            log.error("执行迁移操作失败", e);
            return ResponseResult.fail("执行迁移操作失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取所有可迁移的表列表
     */
    @GetMapping("/tables")
    @Operation(summary = "获取所有可迁移的表列表", description = "从源数据库获取所有可迁移的表")
    public ResponseResult<List<String>> getAvailableTables() {
        try {
            // 构建基本配置
            MigrationConfig config = buildMigrationConfig(null);
            
            // 获取表列表
            List<String> tables = migrationService.getAvailableTables(config);
            return ResponseResult.success("查询成功", tables);
        } catch (Exception e) {
            log.error("获取表列表失败", e);
            return ResponseResult.fail("获取表列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取迁移配置信息
     */
    @GetMapping("/config")
    @Operation(summary = "获取迁移配置信息", description = "获取当前迁移配置信息")
    public ResponseResult<Map<String, Object>> getMigrationConfig() {
        try {
            Map<String, Object> config = new HashMap<>();
            config.put("batchSize", migrationProperties.getBatchSize());
            config.put("enableResume", migrationProperties.isEnableResume());
            config.put("enableValidation", migrationProperties.isEnableValidation());
            config.put("validationSampleRatio", migrationProperties.getValidationSampleRatio());
            
            return ResponseResult.success("查询成功", config);
        } catch (Exception e) {
            log.error("获取迁移配置失败", e);
            return ResponseResult.fail("获取迁移配置失败: " + e.getMessage());
        }
    }
    
    /**
     * 参数校验
     */
    private void validateRequest(MigrationRequest request) {
        String operation = request.getOperation();
        List<String> tableNames = request.getTableNames();
        
        if (operation == null || operation.trim().isEmpty()) {
            throw new IllegalArgumentException("操作类型不能为空");
        }
        
        if (tableNames == null || tableNames.isEmpty()) {
            throw new IllegalArgumentException("表名列表不能为空");
        }
        
        // 校验操作类型
        String op = operation.toUpperCase();
        if (!op.equals("MIGRATE") && !op.equals("PAUSE") && !op.equals("RESUME") && 
            !op.equals("CANCEL") && !op.equals("VALIDATE") && !op.equals("REPORT") && !op.equals("PROGRESS")) {
            throw new IllegalArgumentException("不支持的操作类型: " + operation);
        }
        
        // 校验校验抽样比例
        if (request.getValidationSampleRatio() != null && 
            (request.getValidationSampleRatio() < 0 || request.getValidationSampleRatio() > 1)) {
            throw new IllegalArgumentException("校验抽样比例必须在0-1之间");
        }
    }
    
    /**
     * 构建迁移配置
     */
    private MigrationConfig buildMigrationConfig(MigrationRequest request) {
        MigrationConfig config = new MigrationConfig();
        
        // 设置数据库连接信息
        config.setSourceUrl(migrationProperties.getSource().getUrl());
        config.setSourceUsername(migrationProperties.getSource().getUsername());
        config.setSourcePassword(migrationProperties.getSource().getPassword());
        config.setTargetUrl(migrationProperties.getTarget().getUrl());
        config.setTargetUsername(migrationProperties.getTarget().getUsername());
        config.setTargetPassword(migrationProperties.getTarget().getPassword());
        
        // 设置其他配置
        config.setBatchSize(request != null && request.getBatchSize() != null ? 
                           request.getBatchSize() : migrationProperties.getBatchSize());
        config.setEnableResume(request != null && request.getEnableResume() != null ? 
                              request.getEnableResume() : migrationProperties.isEnableResume());
        config.setEnableValidation(request != null && request.getEnableValidation() != null ? 
                                  request.getEnableValidation() : migrationProperties.isEnableValidation());
        config.setValidationSampleRatio(request != null && request.getValidationSampleRatio() != null ? 
                                       request.getValidationSampleRatio() : migrationProperties.getValidationSampleRatio());
        config.setProgressPath(migrationProperties.getProgressPath());
        config.setReportPath(migrationProperties.getReportPath());
        
        return config;
    }
}
