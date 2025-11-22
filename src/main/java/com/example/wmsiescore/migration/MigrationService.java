package com.example.wmsiescore.migration;

import java.util.List;

/**
 * 数据迁移服务接口
 */
public interface MigrationService {
    
    /**
     * 迁移指定表的数据
     * @param config 迁移配置
     * @param tableName 表名
     * @return 迁移进度
     */
    MigrationProgress migrateTable(MigrationConfig config, String tableName);
    
    /**
     * 迁移多个表的数据
     * @param config 迁移配置
     * @param tableNames 表名列表
     * @return 迁移报告
     */
    MigrationReport migrateTables(MigrationConfig config, List<String> tableNames);
    
    /**
     * 获取迁移进度
     * @param config 迁移配置
     * @param tableName 表名
     * @return 迁移进度
     */
    MigrationProgress getMigrationProgress(MigrationConfig config, String tableName);
    
    /**
     * 暂停迁移
     * @param config 迁移配置
     * @param tableName 表名
     * @return 是否成功
     */
    boolean pauseMigration(MigrationConfig config, String tableName);
    
    /**
     * 恢复迁移
     * @param config 迁移配置
     * @param tableName 表名
     * @return 迁移进度
     */
    MigrationProgress resumeMigration(MigrationConfig config, String tableName);
    
    /**
     * 取消迁移
     * @param config 迁移配置
     * @param tableName 表名
     * @return 是否成功
     */
    boolean cancelMigration(MigrationConfig config, String tableName);
    
    /**
     * 验证迁移结果
     * @param config 迁移配置
     * @param tableName 表名
     * @return 验证结果
     */
    boolean validateMigration(MigrationConfig config, String tableName);
    
    /**
     * 生成迁移报告
     * @param config 迁移配置
     * @param tableNames 表名列表
     * @return 迁移报告
     */
    MigrationReport generateReport(MigrationConfig config, List<String> tableNames);
    
    /**
     * 获取源数据库中所有可迁移的表列表
     * @param config 迁移配置
     * @return 表名列表
     */
    List<String> getAvailableTables(MigrationConfig config);
}