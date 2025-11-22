package com.example.wmsiescore.migration.impl;

import com.example.wmsiescore.migration.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 数据迁移服务实现类
 */
@Slf4j
@Service
public class MigrationServiceImpl implements MigrationService {
    
    // 存储迁移进度的缓存
    private static final Map<String, MigrationProgress> progressCache = new ConcurrentHashMap<>();
    
    // 线程池用于异步执行迁移任务
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    
    // 线程本地变量，用于存储共同列列表
    private static final ThreadLocal<List<String>> commonColumnsThreadLocal = new ThreadLocal<>();
    
    @Override
    public MigrationProgress migrateTable(MigrationConfig config, String tableName) {
        log.info("开始迁移表: {}", tableName);
        
        // 创建迁移进度对象
        MigrationProgress progress = new MigrationProgress();
        progress.setTableName(tableName);
        progress.setStatus("RUNNING");
        progress.setStartTime(LocalDateTime.now());
        
        // 将进度对象放入缓存
        progressCache.put(tableName, progress);
        
        // 异步执行迁移任务
        executorService.submit(() -> {
            try {
                // 执行实际的迁移逻辑
                performMigration(config, tableName, progress);
            } catch (Exception e) {
                log.error("迁移表 {} 失败", tableName, e);
                
                // 记录完整的错误堆栈
                StringBuilder errorStack = new StringBuilder();
                errorStack.append("迁移失败: ").append(e.getMessage()).append("\n");
                
                // 获取堆栈跟踪
                StackTraceElement[] stackTrace = e.getStackTrace();
                for (int i = 0; i < Math.min(stackTrace.length, 20); i++) {
                    errorStack.append("  at ").append(stackTrace[i].toString()).append("\n");
                }
                
                // 如果是SQLException，记录更多详细信息
                if (e instanceof SQLException) {
                    SQLException sqlEx = (SQLException) e;
                    errorStack.append("\nSQL错误详情:\n");
                    errorStack.append("  SQL状态: ").append(sqlEx.getSQLState()).append("\n");
                    errorStack.append("  错误代码: ").append(sqlEx.getErrorCode()).append("\n");
                    errorStack.append("  下一个异常: ").append(sqlEx.getNextException()).append("\n");
                }
                
                String errorMessage = errorStack.toString();
                log.error("完整错误信息:\n{}", errorMessage);
                
                progress.setStatus("FAILED");
                progress.setErrorMessage(errorMessage);
                progress.setEndTime(LocalDateTime.now());
            }
        });
        
        return progress;
    }
    
    @Override
    public MigrationReport migrateTables(MigrationConfig config, List<String> tableNames) {
        log.info("开始批量迁移表: {}", tableNames);
        
        MigrationReport report = new MigrationReport();
        report.setStartTime(LocalDateTime.now());
        report.setTotalTables(tableNames.size());
        
        // 并行迁移所有表
        List<MigrationProgress> progressList = new ArrayList<>();
        for (String tableName : tableNames) {
            MigrationProgress progress = migrateTable(config, tableName);
            progressList.add(progress);
        }
        
        // 等待所有迁移任务完成
        boolean allCompleted = false;
        while (!allCompleted) {
            allCompleted = true;
            for (MigrationProgress progress : progressList) {
                if (progress.isRunning()) {
                    allCompleted = false;
                    try {
                        Thread.sleep(1000); // 等待1秒再检查
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                    break;
                }
            }
        }
        
        // 生成报告
        report.setEndTime(LocalDateTime.now());
        
        // 统计结果
        for (MigrationProgress progress : progressList) {
            MigrationReport.TableMigrationResult tableResult = new MigrationReport.TableMigrationResult();
            tableResult.setTableName(progress.getTableName());
            tableResult.setStatus(progress.getStatus());
            tableResult.setTotalRecords(progress.getTotalRecords());
            tableResult.setSuccessRecords(progress.getMigratedRecords());
            
            if (progress.isCompleted()) {
                report.setSuccessTables(report.getSuccessTables() + 1);
                report.setSuccessRecords(report.getSuccessRecords() + progress.getMigratedRecords());
            } else if (progress.isFailed()) {
                report.setFailedTables(report.getFailedTables() + 1);
                report.setFailedRecords(report.getFailedRecords() + (progress.getTotalRecords() - progress.getMigratedRecords()));
                tableResult.setErrorMessage(progress.getErrorMessage());
            }
            
            report.getTableResults().put(progress.getTableName(), tableResult);
            report.setTotalRecords(report.getTotalRecords() + progress.getTotalRecords());
        }
        
        // 保存报告到文件
        saveReportToFile(config, report);
        
        return report;
    }
    
    @Override
    public MigrationProgress getMigrationProgress(MigrationConfig config, String tableName) {
        // 从缓存中获取进度
        MigrationProgress progress = progressCache.get(tableName);
        
        // 如果缓存中没有，尝试从文件中加载
        if (progress == null && config.isEnableResume()) {
            progress = loadProgressFromFile(config, tableName);
            if (progress != null) {
                progressCache.put(tableName, progress);
            }
        }
        
        return progress;
    }
    
    @Override
    public boolean pauseMigration(MigrationConfig config, String tableName) {
        MigrationProgress progress = progressCache.get(tableName);
        if (progress != null && progress.isRunning()) {
            progress.setStatus("PAUSED");
            saveProgressToFile(config, progress);
            return true;
        }
        return false;
    }
    
    @Override
    public MigrationProgress resumeMigration(MigrationConfig config, String tableName) {
        MigrationProgress progress = getMigrationProgress(config, tableName);
        if (progress != null && "PAUSED".equals(progress.getStatus())) {
            progress.setStatus("RUNNING");
            
            // 异步执行迁移任务
            executorService.submit(() -> {
                try {
                    performMigration(config, tableName, progress);
                } catch (Exception e) {
                    log.error("恢复迁移表 {} 失败", tableName, e);
                    progress.setStatus("FAILED");
                    progress.setErrorMessage(e.getMessage());
                    progress.setEndTime(LocalDateTime.now());
                }
            });
        }
        return progress;
    }
    
    @Override
    public boolean cancelMigration(MigrationConfig config, String tableName) {
        MigrationProgress progress = progressCache.get(tableName);
        if (progress != null) {
            progress.setStatus("CANCELLED");
            progress.setEndTime(LocalDateTime.now());
            saveProgressToFile(config, progress);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean validateMigration(MigrationConfig config, String tableName) {
        if (!config.isEnableValidation()) {
            return true;
        }
        
        log.info("开始验证表 {} 的迁移结果", tableName);
        
        try (Connection sourceConn = getSourceConnection(config);
             Connection targetConn = getTargetConnection(config)) {
            
            // 获取源表和目标表的记录数
            long sourceCount = getTableRecordCount(sourceConn, tableName);
            long targetCount = getTableRecordCount(targetConn, tableName);
            
            if (sourceCount != targetCount) {
                log.error("表 {} 迁移验证失败: 源表记录数 {}, 目标表记录数 {}", tableName, sourceCount, targetCount);
                return false;
            }
            
            // 抽样验证数据一致性
            if (config.getValidationSampleRatio() > 0) {
                return validateSampleData(config, tableName);
            }
            
            return true;
        } catch (SQLException e) {
            log.error("验证表 {} 的迁移结果时发生错误", tableName, e);
            return false;
        }
    }
    
    @Override
    public MigrationReport generateReport(MigrationConfig config, List<String> tableNames) {
        MigrationReport report = new MigrationReport();
        report.setStartTime(LocalDateTime.now());
        report.setTotalTables(tableNames.size());
        
        for (String tableName : tableNames) {
            MigrationProgress progress = getMigrationProgress(config, tableName);
            if (progress != null) {
                MigrationReport.TableMigrationResult tableResult = new MigrationReport.TableMigrationResult();
                tableResult.setTableName(tableName);
                tableResult.setStatus(progress.getStatus());
                tableResult.setTotalRecords(progress.getTotalRecords());
                tableResult.setSuccessRecords(progress.getMigratedRecords());
                
                if (progress.isCompleted()) {
                    report.setSuccessTables(report.getSuccessTables() + 1);
                    report.setSuccessRecords(report.getSuccessRecords() + progress.getMigratedRecords());
                } else if (progress.isFailed()) {
                    report.setFailedTables(report.getFailedTables() + 1);
                    report.setFailedRecords(report.getFailedRecords() + (progress.getTotalRecords() - progress.getMigratedRecords()));
                    tableResult.setErrorMessage(progress.getErrorMessage());
                }
                
                report.getTableResults().put(tableName, tableResult);
                report.setTotalRecords(report.getTotalRecords() + progress.getTotalRecords());
            }
        }
        
        report.setEndTime(LocalDateTime.now());
        
        return report;
    }
    
    /**
     * 执行实际的迁移逻辑
     */
    private void performMigration(MigrationConfig config, String tableName, MigrationProgress progress) throws SQLException {
        try {
            try (Connection sourceConn = getSourceConnection(config);
                 Connection targetConn = getTargetConnection(config)) {
                
                // 记录连接信息
                String sourceDatabase = getCurrentDatabase(sourceConn);
                String targetDatabase = getCurrentDatabase(targetConn);
                log.info("源数据库: {}, 目标数据库: {}, 迁移表: {}", sourceDatabase, targetDatabase, tableName);
                
                // 检查目标表是否存在，不存在则创建
                TableExistResult tableExistResult = tableExists(targetConn, tableName);
                
                if (!tableExistResult.exists()) {
                    if (tableExistResult.isReliable()) {
                        log.info("目标表 {} 不存在，正在创建...", tableName);
                        createTable(sourceConn, targetConn, tableName);
                        progress.setTableCreated(true);
                        
                        // 创建后再次检查表是否存在
                        TableExistResult afterCreateResult = tableExists(targetConn, tableName);
                        if (!afterCreateResult.exists()) {
                            throw new SQLException("创建表后仍无法找到表: " + tableName);
                        }
                        log.info("表 {} 创建成功", tableName);
                    } else {
                        // 检查结果不可靠，可能是连接问题或其他错误
                        throw new SQLException("无法确定目标表 " + tableName + " 是否存在: " + tableExistResult.getMessage());
                    }
                } else {
                    log.info("目标表 {} 已存在，跳过创建步骤", tableName);
                }
                
                // 获取源表总记录数
                long totalRecords = getTableRecordCount(sourceConn, tableName);
                progress.setTotalRecords(totalRecords);
                
                // 如果启用断点续传，获取上次迁移的位置
                Object lastMigratedKey = null;
                if (config.isEnableResume() && progress.getLastMigratedKey() != null) {
                    lastMigratedKey = progress.getLastMigratedKey();
                    log.info("从上次迁移位置继续: {}", lastMigratedKey);
                }
                
                // 获取主键列名
                String primaryKeyColumn = getPrimaryKeyColumn(sourceConn, tableName);
                
                // 检查目标表是否已有数据，获取最大ID值
                Long maxIdInTarget = getMaxIdInTable(targetConn, tableName, primaryKeyColumn);
                log.info("目标表 {} 当前最大ID值: {}", tableName, maxIdInTarget);
                
                // 获取目标表中已存在的主键值集合，用于快速判断冲突
                Set<Object> existingIds = new HashSet<>();
                if (maxIdInTarget != null && maxIdInTarget > 0) {
                    existingIds = getExistingIds(targetConn, tableName, primaryKeyColumn);
                    log.info("目标表 {} 已有 {} 条记录", tableName, existingIds.size());
                }
                
                // 检查主键列是否是自增列
                boolean isAutoIncrement = isAutoIncrementColumn(targetConn, tableName, primaryKeyColumn);
                log.info("表 {} 的主键列 {} 是否自增: {}", tableName, primaryKeyColumn, isAutoIncrement);
                
                // 如果是自增列，临时禁用自增属性以确保ID一致
                if (isAutoIncrement) {
                    disableAutoIncrement(targetConn, tableName, primaryKeyColumn);
                }
                
                // 分批迁移数据
                AtomicLong migratedCount = new AtomicLong(0);
                AtomicLong skippedCount = new AtomicLong(0);
                
                // 修改查询SQL，如果目标表已有数据，则只查询大于最大ID的记录
                String sql;
                if (maxIdInTarget != null && maxIdInTarget > 0) {
                    sql = "SELECT * FROM " + tableName + " WHERE " + primaryKeyColumn + " > " + maxIdInTarget;
                    if (lastMigratedKey != null) {
                        sql += " AND " + primaryKeyColumn + " > " + lastMigratedKey;
                    }
                    sql += " ORDER BY " + primaryKeyColumn + " LIMIT " + config.getBatchSize();
                } else {
                    sql = buildSelectSql(tableName, primaryKeyColumn, lastMigratedKey, config.getBatchSize());
                }
                
                // 记录构建的SQL语句用于调试
                log.info("构建的SQL查询语句: {}", sql);
                
                // 验证SQL语句的语法
                if (sql == null || sql.trim().isEmpty()) {
                    throw new SQLException("构建的SQL语句为空");
                }
                
                // 检查SQL语句是否包含语法错误
                if (!sql.toUpperCase().contains("SELECT") || !sql.toUpperCase().contains("FROM")) {
                    throw new SQLException("构建的SQL语句语法错误: " + sql);
                }
                
                // 验证SQL语句的语法
                if (sql == null || sql.trim().isEmpty()) {
                    throw new SQLException("构建的SQL语句为空");
                }
                
                // 检查SQL语句是否包含语法错误
                if (!sql.toUpperCase().contains("SELECT") || !sql.toUpperCase().contains("FROM")) {
                    throw new SQLException("构建的SQL语句语法错误: " + sql);
                }
                
                // 在开始迁移前，再次验证表是否存在
                TableExistResult finalCheckResult = tableExists(targetConn, tableName);
                if (!finalCheckResult.exists()) {
                    throw new SQLException("在开始数据迁移前发现表不存在: " + tableName + ", 数据库: " + targetDatabase);
                }
                
                try (PreparedStatement selectStmt = sourceConn.prepareStatement(sql);
                     ResultSet rs = selectStmt.executeQuery()) {
                    
                    // 获取目标表的插入语句，保持主键ID一致
                    String insertSql = buildInsertSqlWithPrimaryKey(sourceConn, targetConn, tableName, primaryKeyColumn);
                    try (PreparedStatement insertStmt = targetConn.prepareStatement(insertSql)) {
                    
                    targetConn.setAutoCommit(false);
                    int batchCount = 0;
                        
                        while (rs.next()) {
                            // 检查当前记录的主键值是否已存在
                            Object currentId = rs.getObject(primaryKeyColumn);
                            if (existingIds.contains(currentId)) {
                                log.debug("跳过已存在的记录，表: {}, ID: {}", tableName, currentId);
                                skippedCount.incrementAndGet();
                                continue; // 跳过已存在的记录
                            }
                            
                            // 设置插入语句的参数（包含主键，保持ID一致）
                            setInsertStatementParametersWithPrimaryKey(rs, insertStmt, tableName, primaryKeyColumn);
                            insertStmt.addBatch();
                            
                            batchCount++;
                            
                            // 执行批量插入
                            if (batchCount % config.getBatchSize() == 0) {
                                try {
                                    insertStmt.executeBatch();
                                    targetConn.commit();
                                    log.debug("成功提交批次，记录数: {}", batchCount);
                                } catch (SQLException e) {
                                    // 处理主键冲突
                                    if (e.getMessage() != null && e.getMessage().contains("Duplicate entry")) {
                                        log.warn("检测到主键冲突，尝试逐条插入: {}", e.getMessage());
                                        handleBatchInsertWithConflict(targetConn, insertStmt, rs, tableName, primaryKeyColumn, batchCount);
                                    } else {
                                        targetConn.rollback();
                                        throw e;
                                    }
                                }
                                
                                // 更新进度
                                long currentCount = migratedCount.addAndGet(batchCount);
                                progress.setMigratedRecords(currentCount);
                                progress.setLastMigratedKey(rs.getObject(primaryKeyColumn));
                                
                                // 保存进度到文件
                                if (config.isEnableResume() && currentCount % (config.getBatchSize() * 10) == 0) {
                                    saveProgressToFile(config, progress);
                                }
                                
                                batchCount = 0;
                            }
                        }
                        
                        // 处理剩余的批次
                        if (batchCount > 0) {
                            try {
                                insertStmt.executeBatch();
                                targetConn.commit();
                                migratedCount.addAndGet(batchCount);
                                log.debug("成功提交最后批次，记录数: {}", batchCount);
                            } catch (SQLException e) {
                                // 处理主键冲突
                                if (e.getMessage() != null && e.getMessage().contains("Duplicate entry")) {
                                    log.warn("检测到主键冲突，尝试逐条插入: {}", e.getMessage());
                                    handleBatchInsertWithConflict(targetConn, insertStmt, rs, tableName, primaryKeyColumn, batchCount);
                                } else {
                                    targetConn.rollback();
                                    throw e;
                                }
                            }
                        }
                    }
                }
                
                // 记录跳过的记录数
                if (skippedCount.get() > 0) {
                    log.info("表 {} 迁移完成，跳过 {} 条已存在的记录", tableName, skippedCount.get());
                }
                
                // 更新最终进度
                progress.setMigratedRecords(migratedCount.get());
                progress.setStatus("COMPLETED");
                progress.setEndTime(LocalDateTime.now());
                
                // 保存最终进度
                if (config.isEnableResume()) {
                    saveProgressToFile(config, progress);
                }
                
                // 执行数据校验
                if (config.isEnableValidation()) {
                    boolean isValid = validateMigration(config, tableName);
                    if (!isValid) {
                        progress.setStatus("VALIDATION_FAILED");
                        progress.setErrorMessage("数据校验失败");
                    }
                }
                
                log.info("表 {} 迁移完成，共迁移 {} 条记录", tableName, migratedCount.get());
                
                // 恢复自增属性（如果之前禁用了）
                if (isAutoIncrement) {
                    enableAutoIncrement(targetConn, tableName, primaryKeyColumn);
                }
            } catch (Exception e) {
                // 发生异常时，尝试恢复自增属性
//                try {
//                    if (isAutoIncrement) {
//                        enableAutoIncrement(targetConn, tableName, primaryKeyColumn);
//                    }
//                } catch (SQLException ex) {
//                    log.error("恢复自增属性失败: {}", ex.getMessage());
//                }
                throw e;
            }
        } finally {
            // 清理ThreadLocal变量，防止内存泄漏
            commonColumnsThreadLocal.remove();
        }
    }
    
    /**
     * 获取源数据库连接
     */
    private Connection getSourceConnection(MigrationConfig config) throws SQLException {
        try {
            return DriverManager.getConnection(config.getSourceUrl(), config.getSourceUsername(), config.getSourcePassword());
        } catch (SQLException e) {
            log.error("获取源数据库连接失败", e);
            throw e;
        }
    }
    
    /**
     * 获取目标数据库连接
     */
    private Connection getTargetConnection(MigrationConfig config) throws SQLException {
        try {
            return DriverManager.getConnection(config.getTargetUrl(), config.getTargetUsername(), config.getTargetPassword());
        } catch (SQLException e) {
            log.error("获取目标数据库连接失败", e);
            throw e;
        }
    }
    
    /**
     * 检查表是否存在
     * 仅通过查询数据库系统表的方式来判断目标表是否存在
     * 
     * @param conn 数据库连接
     * @param tableName 表名
     * @return 表存在检查结果
     * @throws SQLException 数据库异常
     */
    private TableExistResult tableExists(Connection conn, String tableName) throws SQLException {
        // 参数校验
        if (conn == null) {
            throw new SQLException("数据库连接为空");
        }
        
        if (tableName == null || tableName.trim().isEmpty()) {
            throw new SQLException("表名为空");
        }
        
        // 获取当前连接的数据库名称
        String currentDatabase = getCurrentDatabase(conn);
        log.info("检查表是否存在 - 数据库: {}, 表名: {}", currentDatabase, tableName);
        
        // 使用information_schema查询表是否存在
        String sql = "SELECT COUNT(*) FROM information_schema.tables " +
                     "WHERE table_schema = ? AND table_name = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, currentDatabase);
            stmt.setString(2, tableName);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    boolean exists = count > 0;
                    
                    if (exists) {
                        log.info("表 {} 存在于数据库 {}", tableName, currentDatabase);
                        return new TableExistResult(true, "表存在于数据库 " + currentDatabase, true);
                    } else {
                        log.info("表 {} 在数据库 {} 中不存在", tableName, currentDatabase);
                        return new TableExistResult(false, "表在数据库 " + currentDatabase + " 中不存在", true);
                    }
                }
            }
        }
        
        // 如果精确查询失败，尝试不区分大小写的查询
        String caseInsensitiveSql = "SELECT COUNT(*) FROM information_schema.tables " +
                                   "WHERE table_schema = ? AND LOWER(table_name) = LOWER(?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(caseInsensitiveSql)) {
            stmt.setString(1, currentDatabase);
            stmt.setString(2, tableName);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    boolean exists = count > 0;
                    
                    if (exists) {
                        log.info("表 {} 存在于数据库 {} (不区分大小写匹配)", tableName, currentDatabase);
                        return new TableExistResult(true, "表存在于数据库 " + currentDatabase + " (不区分大小写匹配)", true);
                    } else {
                        log.info("表 {} 在数据库 {} 中不存在 (不区分大小写匹配)", tableName, currentDatabase);
                        return new TableExistResult(false, "表在数据库 " + currentDatabase + " 中不存在 (不区分大小写匹配)", true);
                    }
                }
            }
        }
        
        // 如果还是查询不到，尝试模糊查询
        String fuzzySql = "SELECT table_name FROM information_schema.tables " +
                         "WHERE table_schema = ? AND table_name LIKE ? LIMIT 5";
        
        try (PreparedStatement stmt = conn.prepareStatement(fuzzySql)) {
            stmt.setString(1, currentDatabase);
            stmt.setString(2, "%" + tableName + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<String> similarTables = new ArrayList<>();
                while (rs.next()) {
                    similarTables.add(rs.getString("table_name"));
                }
                
                if (!similarTables.isEmpty()) {
                    log.warn("表 {} 在数据库 {} 中不存在，但找到相似的表: {}", tableName, currentDatabase, similarTables);
                    return new TableExistResult(false, "表在数据库 " + currentDatabase + " 中不存在，但找到相似的表: " + similarTables, true);
                } else {
                    log.info("表 {} 在数据库 {} 中不存在，也未找到相似的表", tableName, currentDatabase);
                    return new TableExistResult(false, "表在数据库 " + currentDatabase + " 中不存在，也未找到相似的表", true);
                }
            }
        }
    }
    

    
    /**
     * 获取当前连接的数据库名称
     * 
     * @param conn 数据库连接
     * @return 数据库名称
     * @throws SQLException 数据库异常
     */
    private String getCurrentDatabase(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT DATABASE()")) {
            if (rs.next()) {
                return rs.getString(1);
            }
            throw new SQLException("无法获取当前数据库名称");
        }
    }
    
    /**
     * 表存在检查结果内部类
     */
    private static class TableExistResult {
        private final boolean exists;
        private final String message;
        private final boolean reliable;
        
        public TableExistResult(boolean exists, String message, boolean reliable) {
            this.exists = exists;
            this.message = message;
            this.reliable = reliable;
        }
        
        public boolean exists() {
            return exists;
        }
        
        public String getMessage() {
            return message;
        }
        
        public boolean isReliable() {
            return reliable;
        }
    }
    
    /**
     * 创建表
     */
    private void createTable(Connection sourceConn, Connection targetConn, String tableName) throws SQLException {
        // 获取源表的创建语句
        String createTableSql = getCreateTableSql(sourceConn, tableName);
        
        // 在目标数据库中执行创建语句
        try (Statement stmt = targetConn.createStatement()) {
            stmt.execute(createTableSql);
        }
    }
    
    /**
     * 获取表的创建语句
     * 使用SHOW CREATE TABLE命令获取完整的建表SQL语句
     */
    private String getCreateTableSql(Connection conn, String tableName) throws SQLException {
        // 参数校验
        if (conn == null) {
            throw new SQLException("数据库连接为空");
        }
        
        if (tableName == null || tableName.trim().isEmpty()) {
            throw new SQLException("表名为空");
        }
        
        // 检查表是否存在
        TableExistResult tableExistResult = tableExists(conn, tableName);
        if (!tableExistResult.exists()) {
            if (tableExistResult.isReliable()) {
                throw new SQLException("表 " + tableName + " 不存在");
            } else {
                throw new SQLException("无法确定表 " + tableName + " 是否存在: " + tableExistResult.getMessage());
            }
        }
        
        // 执行SHOW CREATE TABLE命令
        String sql = "SHOW CREATE TABLE " + escapeTableName(tableName);
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                // 获取建表SQL语句
                String createTableSql = rs.getString(2); // 第二列是建表语句
                
                if (createTableSql == null || createTableSql.trim().isEmpty()) {
                    throw new SQLException("获取表 " + tableName + " 的建表语句失败");
                }
                
                // 格式化SQL语句
                String s = formatCreateTableSql(createTableSql);
                return s;
            } else {
                throw new SQLException("未找到表 " + tableName + " 的建表语句");
            }
        } catch (SQLException e) {
            log.error("执行SHOW CREATE TABLE命令失败: {}", e.getMessage());
            throw new SQLException("获取表 " + tableName + " 的建表语句失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 转义表名，防止SQL注入
     */
    private String escapeTableName(String tableName) {
        if (tableName == null || tableName.trim().isEmpty()) {
            return "";
        }
        
        // 移除可能的危险字符
        String escaped = tableName.replaceAll("[;\"'\\\\]", "");
        
        // 如果表名包含特殊字符或关键字，使用反引号包围
        if (escaped.contains(" ") || escaped.contains("-") || isSqlKeyword(escaped)) {
            return "`" + escaped + "`";
        }
        
        return escaped;
    }
    
    /**
     * 检查字符串是否是SQL关键字
     */
    private boolean isSqlKeyword(String word) {
        // 常见MySQL关键字列表
        Set<String> keywords = new HashSet<>(Arrays.asList(
            "SELECT", "FROM", "WHERE", "INSERT", "UPDATE", "DELETE", "CREATE", "TABLE", 
            "INDEX", "VIEW", "DROP", "ALTER", "ADD", "COLUMN", "PRIMARY", "KEY",
            "FOREIGN", "REFERENCES", "UNIQUE", "NOT", "NULL", "DEFAULT", "AUTO_INCREMENT",
            "INT", "VARCHAR", "TEXT", "DATETIME", "TIMESTAMP", "BOOLEAN", "DECIMAL"
        ));
        
        return keywords.contains(word.toUpperCase());
    }
    
    /**
     * 格式化建表SQL语句
     */
    private String formatCreateTableSql(String originalSql) {
        if (originalSql == null || originalSql.trim().isEmpty()) {
            return "";
        }
        
        // 移除表名前的数据库名前缀（如果有）
        String formattedSql = originalSql.replaceAll("CREATE TABLE `[^`]+`\\.", "CREATE TABLE ");
        
        // 确保SQL语句以分号结尾
        if (!formattedSql.trim().endsWith(";")) {
            formattedSql = formattedSql.trim() + ";";
        }
        
        // 格式化缩进（简单实现）
        formattedSql = formattedSql.replaceAll("\\(\\s*", "(\n    ");
        formattedSql = formattedSql.replaceAll(",\\s*", ",\n    ");
        formattedSql = formattedSql.replaceAll("\\)\\s*(ENGINE|DEFAULT|CHARSET|COLLATE)", ")\n$1");
        
        return formattedSql;
    }
    
    /**
     * 获取表的记录数
     */
    private long getTableRecordCount(Connection conn, String tableName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        }
    }
    
    /**
     * 获取主键列名
     */
    private String getPrimaryKeyColumn(Connection conn, String tableName) throws SQLException {
        try (ResultSet rs = conn.getMetaData().getPrimaryKeys(null, null, tableName)) {
            if (rs.next()) {
                return rs.getString("COLUMN_NAME");
            }
            throw new SQLException("表 " + tableName + " 没有主键");
        }
    }
    
    /**
     * 检查列是否是自增列
     */
    private boolean isAutoIncrementColumn(Connection conn, String tableName, String columnName) throws SQLException {
        try (ResultSet rs = conn.getMetaData().getColumns(null, null, tableName, columnName)) {
            if (rs.next()) {
                // "YES"表示该列是自增列
                return "YES".equalsIgnoreCase(rs.getString("IS_AUTOINCREMENT"));
            }
            return false;
        }
    }
    
    /**
     * 临时禁用表的自增属性，以便插入指定ID
     */
    private void disableAutoIncrement(Connection conn, String tableName, String primaryKeyColumn) throws SQLException {
        // 检查主键列是否是自增列
        if (!isAutoIncrementColumn(conn, tableName, primaryKeyColumn)) {
            log.debug("表 {} 的主键列 {} 不是自增列，无需禁用", tableName, primaryKeyColumn);
            return;
        }
        
        // 构建禁用自增的SQL语句
        String sql = String.format("ALTER TABLE %s MODIFY %s BIGINT NOT NULL", tableName, primaryKeyColumn);
        
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            log.info("已临时禁用表 {} 的自增属性", tableName);
        } catch (SQLException e) {
            log.error("禁用表 {} 的自增属性失败: {}", tableName, e.getMessage());
            throw e;
        }
    }
    
    /**
     * 恢复表的自增属性
     */
    private void enableAutoIncrement(Connection conn, String tableName, String primaryKeyColumn) throws SQLException {
        // 检查主键列是否是自增列
        if (!isAutoIncrementColumn(conn, tableName, primaryKeyColumn)) {
            log.debug("表 {} 的主键列 {} 不是自增列，无需恢复", tableName, primaryKeyColumn);
            return;
        }
        
        // 获取当前最大ID值
        Long maxId = getMaxIdInTable(conn, tableName, primaryKeyColumn);
        long nextId = (maxId != null ? maxId + 1 : 1);
        
        // 构建恢复自增的SQL语句
        String sql = String.format("ALTER TABLE %s MODIFY %s BIGINT NOT NULL AUTO_INCREMENT", tableName, primaryKeyColumn);
        
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            
            // 设置自增起始值
            String setAutoIncrementSql = String.format("ALTER TABLE %s AUTO_INCREMENT = %d", tableName, nextId);
            stmt.execute(setAutoIncrementSql);
            
            log.info("已恢复表 {} 的自增属性，起始值: {}", tableName, nextId);
        } catch (SQLException e) {
            log.error("恢复表 {} 的自增属性失败: {}", tableName, e.getMessage());
            throw e;
        }
    }
    
    /**
     * 获取表中指定列的最大值
     */
    private Long getMaxIdInTable(Connection conn, String tableName, String columnName) throws SQLException {
        String sql = "SELECT MAX(" + columnName + ") FROM " + tableName;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getLong(1);
            }
            return null;
        }
    }
    
    /**
     * 获取表中已存在的主键值集合
     */
    private Set<Object> getExistingIds(Connection conn, String tableName, String primaryKeyColumn) throws SQLException {
        Set<Object> existingIds = new HashSet<>();
        
        // 对于大表，我们不应该加载所有ID到内存中
        // 这里我们只加载最近的一部分ID，用于冲突检测
        String sql = "SELECT " + primaryKeyColumn + " FROM " + tableName + 
                    " ORDER BY " + primaryKeyColumn + " DESC LIMIT 10000";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                existingIds.add(rs.getObject(1));
            }
        }
        
        return existingIds;
    }
    
    /**
     * 处理批量插入中的主键冲突
     */
    private void handleBatchInsertWithConflict(Connection conn, PreparedStatement insertStmt, 
                                             ResultSet rs, String tableName, 
                                             String primaryKeyColumn, int batchSize) throws SQLException {
        log.warn("处理表 {} 的主键冲突，切换到逐条插入模式", tableName);
        
        // 回滚当前事务
        conn.rollback();
        
        // 由于ResultSet已经向前移动，我们需要重新查询当前批次的数据
        // 获取当前记录的主键值，用于重新查询
        Object currentId = rs.getObject(primaryKeyColumn);
        
        // 构建重新查询的SQL，获取当前批次的数据
        String requerySql = "SELECT * FROM " + tableName + " WHERE " + 
                           primaryKeyColumn + " >= ? ORDER BY " + primaryKeyColumn + " LIMIT " + batchSize;
        
        try (PreparedStatement requeryStmt = conn.prepareStatement(requerySql)) {
            requeryStmt.setObject(1, currentId);
            
            try (ResultSet requeryRs = requeryStmt.executeQuery()) {
                int successCount = 0;
                int skipCount = 0;
                
                while (requeryRs.next()) {
                    try {
                        // 设置插入语句的参数（包含主键，确保ID一致）
                        setInsertStatementParametersWithPrimaryKey(requeryRs, insertStmt, tableName, primaryKeyColumn);
                        
                        // 尝试单条插入
                        insertStmt.executeUpdate();
                        successCount++;
                    } catch (SQLException e) {
                        // 检查是否是主键冲突
                        if (e.getMessage() != null && e.getMessage().contains("Duplicate entry")) {
                            Object duplicateId = requeryRs.getObject(primaryKeyColumn);
                            log.debug("跳过重复记录，表: {}, ID: {}", tableName, duplicateId);
                            skipCount++;
                        } else {
                            // 其他类型的错误，重新抛出
                            log.error("插入记录时发生错误: {}", e.getMessage());
                            throw e;
                        }
                    }
                }
                
                // 提交成功插入的记录
                conn.commit();
                
                log.info("表 {} 冲突处理完成，成功插入 {} 条记录，跳过 {} 条重复记录", 
                        tableName, successCount, skipCount);
            }
        }
    }
    
    /**
     * 构建查询SQL
     */
    private String buildSelectSql(String tableName, String primaryKeyColumn, Object lastMigratedKey, int batchSize) {
        StringBuilder sql = new StringBuilder("SELECT * FROM ").append(tableName);
        
        if (lastMigratedKey != null) {
            sql.append(" WHERE ").append(primaryKeyColumn).append(" > ").append(lastMigratedKey);
        }
        
        sql.append(" ORDER BY ").append(primaryKeyColumn).append(" LIMIT ").append(batchSize);
        
        String finalSql = sql.toString();
        log.debug("buildSelectSql构建的SQL: {}", finalSql);
        return finalSql;
    }
    
    /**
     * 构建插入SQL
     */
    private String buildInsertSql(Connection sourceConn, Connection targetConn, String tableName) throws SQLException {
        // 获取源表和目标数据库的名称
        String sourceDatabase = getCurrentDatabase(sourceConn);
        String targetDatabase = getCurrentDatabase(targetConn);
        
        log.info("构建插入SQL - 源数据库: {}, 目标数据库: {}, 表名: {}", sourceDatabase, targetDatabase, tableName);
        
        // 获取源表的所有列，使用LinkedHashSet避免重复并保持顺序
        Set<String> sourceColumns = new LinkedHashSet<>();
        try (ResultSet rs = sourceConn.getMetaData().getColumns(null, null, tableName, null)) {
            while (rs.next()) {
                sourceColumns.add(rs.getString("COLUMN_NAME"));
            }
        }
        
        // 获取目标表的所有列，使用LinkedHashSet避免重复并保持顺序
        Set<String> targetColumns = new LinkedHashSet<>();
        try (ResultSet rs = targetConn.getMetaData().getColumns(null, null, tableName, null)) {
            while (rs.next()) {
                targetColumns.add(rs.getString("COLUMN_NAME"));
            }
        }
        
        log.debug("源表列数: {}, 目标表列数: {}", sourceColumns.size(), targetColumns.size());
        log.debug("源表列: {}", sourceColumns);
        log.debug("目标表列: {}", targetColumns);
        
        // 找出源表和目标表共有的列
        List<String> commonColumns = new ArrayList<>();
        for (String sourceColumn : sourceColumns) {
            if (targetColumns.contains(sourceColumn)) {
                commonColumns.add(sourceColumn);
            }
        }
        
        if (commonColumns.isEmpty()) {
            throw new SQLException("源表和目标表没有共同的列");
        }
        
        // 检查是否有重复的列名
        Set<String> uniqueColumns = new HashSet<>(commonColumns);
        if (uniqueColumns.size() != commonColumns.size()) {
            // 找出重复的列名
            Set<String> duplicates = new HashSet<>();
            Set<String> seen = new HashSet<>();
            for (String column : commonColumns) {
                if (!seen.add(column)) {
                    duplicates.add(column);
                }
            }
            throw new SQLException("发现重复的列名: " + duplicates);
        }
        
        // 构建插入语句
        StringBuilder sql = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
        
        for (int i = 0; i < commonColumns.size(); i++) {
            if (i > 0) {
                sql.append(", ");
            }
            sql.append(commonColumns.get(i));
        }
        
        sql.append(") VALUES (");
        
        for (int i = 0; i < commonColumns.size(); i++) {
            if (i > 0) {
                sql.append(", ");
            }
            sql.append("?");
        }
        
        sql.append(")");
        
        log.info("构建的插入SQL: {}", sql.toString());
        log.info("共同列数: {}", commonColumns.size());
        log.info("共同列列表: {}", commonColumns);
        
        // 将共同列列表存储在ThreadLocal中，供setInsertStatementParameters方法使用
        commonColumnsThreadLocal.set(commonColumns);
        
        return sql.toString();
    }
    
    /**
     * 构建不包含自增主键的插入SQL
     * 用于避免主键冲突，让数据库自动生成ID
     */
    private String buildInsertSqlWithoutAutoIncrement(Connection sourceConn, Connection targetConn, String tableName, String primaryKeyColumn) throws SQLException {
        // 获取源表和目标数据库的名称
        String sourceDatabase = getCurrentDatabase(sourceConn);
        String targetDatabase = getCurrentDatabase(targetConn);
        
        log.info("构建不包含自增主键的插入SQL - 源数据库: {}, 目标数据库: {}, 表名: {}, 主键列: {}", 
                sourceDatabase, targetDatabase, tableName, primaryKeyColumn);
        
        // 检查主键列是否是自增列
        boolean isAutoIncrement = isAutoIncrementColumn(targetConn, tableName, primaryKeyColumn);
        log.info("主键列 {} 是否自增: {}", primaryKeyColumn, isAutoIncrement);
        
        // 获取源表的所有列，使用LinkedHashSet避免重复并保持顺序
        Set<String> sourceColumns = new LinkedHashSet<>();
        try (ResultSet rs = sourceConn.getMetaData().getColumns(null, null, tableName, null)) {
            while (rs.next()) {
                sourceColumns.add(rs.getString("COLUMN_NAME"));
            }
        }
        
        // 获取目标表的所有列，使用LinkedHashSet避免重复并保持顺序
        Set<String> targetColumns = new LinkedHashSet<>();
        try (ResultSet rs = targetConn.getMetaData().getColumns(null, null, tableName, null)) {
            while (rs.next()) {
                targetColumns.add(rs.getString("COLUMN_NAME"));
            }
        }
        
        // 找出源表和目标表共有的列
        List<String> commonColumns = new ArrayList<>();
        for (String sourceColumn : sourceColumns) {
            if (targetColumns.contains(sourceColumn)) {
                // 如果是自增主键，则排除
                if (sourceColumn.equals(primaryKeyColumn) && isAutoIncrement) {
                    log.debug("排除自增主键列: {}", sourceColumn);
                    continue;
                }
                commonColumns.add(sourceColumn);
            }
        }
        
        if (commonColumns.isEmpty()) {
            throw new SQLException("源表和目标表没有共同的列（排除自增主键后）");
        }
        
        // 构建插入语句
        StringBuilder sql = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
        
        for (int i = 0; i < commonColumns.size(); i++) {
            if (i > 0) {
                sql.append(", ");
            }
            sql.append(commonColumns.get(i));
        }
        
        sql.append(") VALUES (");
        
        for (int i = 0; i < commonColumns.size(); i++) {
            if (i > 0) {
                sql.append(", ");
            }
            sql.append("?");
        }
        
        sql.append(")");
        
        log.info("构建的不包含自增主键的插入SQL: {}", sql.toString());
        log.info("共同列数（排除自增主键）: {}", commonColumns.size());
        log.info("共同列列表（排除自增主键）: {}", commonColumns);
        
        // 将共同列列表存储在ThreadLocal中，供setInsertStatementParametersWithoutAutoIncrement方法使用
        commonColumnsThreadLocal.set(commonColumns);
        
        return sql.toString();
    }
    
    /**
     * 构建包含主键的插入SQL，确保主键ID与源数据保持一致
     * 在插入前会临时禁用自增属性，插入后再恢复
     */
    private String buildInsertSqlWithPrimaryKey(Connection sourceConn, Connection targetConn, String tableName, String primaryKeyColumn) throws SQLException {
        // 获取源表和目标数据库的名称
        String sourceDatabase = getCurrentDatabase(sourceConn);
        String targetDatabase = getCurrentDatabase(targetConn);
        
        log.info("构建包含主键的插入SQL - 源数据库: {}, 目标数据库: {}, 表名: {}, 主键列: {}", 
                sourceDatabase, targetDatabase, tableName, primaryKeyColumn);
        
        // 获取源表的所有列，使用LinkedHashSet避免重复并保持顺序
        Set<String> sourceColumns = new LinkedHashSet<>();
        try (ResultSet rs = sourceConn.getMetaData().getColumns(null, null, tableName, null)) {
            while (rs.next()) {
                sourceColumns.add(rs.getString("COLUMN_NAME"));
            }
        }
        
        // 获取目标表的所有列，使用LinkedHashSet避免重复并保持顺序
        Set<String> targetColumns = new LinkedHashSet<>();
        try (ResultSet rs = targetConn.getMetaData().getColumns(null, null, tableName, null)) {
            while (rs.next()) {
                targetColumns.add(rs.getString("COLUMN_NAME"));
            }
        }
        
        // 找出源表和目标表共有的列
        List<String> commonColumns = new ArrayList<>();
        for (String sourceColumn : sourceColumns) {
            if (targetColumns.contains(sourceColumn)) {
                commonColumns.add(sourceColumn);
            }
        }
        
        if (commonColumns.isEmpty()) {
            throw new SQLException("源表和目标表没有共同的列");
        }
        
        // 构建插入语句
        StringBuilder sql = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
        
        for (int i = 0; i < commonColumns.size(); i++) {
            if (i > 0) {
                sql.append(", ");
            }
            sql.append(commonColumns.get(i));
        }
        
        sql.append(") VALUES (");
        
        for (int i = 0; i < commonColumns.size(); i++) {
            if (i > 0) {
                sql.append(", ");
            }
            sql.append("?");
        }
        
        sql.append(")");
        
        log.info("构建的包含主键的插入SQL: {}", sql.toString());
        log.info("共同列数: {}", commonColumns.size());
        log.info("共同列列表: {}", commonColumns);
        
        // 将共同列列表存储在ThreadLocal中，供setInsertStatementParametersWithPrimaryKey方法使用
        commonColumnsThreadLocal.set(commonColumns);
        
        return sql.toString();
    }
    
    /**
     * 设置插入语句的参数
     */
    private void setInsertStatementParameters(ResultSet rs, PreparedStatement insertStmt, String tableName) throws SQLException {
        // 获取共同列列表
        List<String> commonColumns = commonColumnsThreadLocal.get();
        if (commonColumns == null || commonColumns.isEmpty()) {
            throw new SQLException("共同列列表为空");
        }
        
        // 获取结果集的元数据
        ResultSetMetaData metaData = rs.getMetaData();
        
        // 创建列名到索引的映射，提高查找效率
        Map<String, Integer> columnIndexMap = new HashMap<>();
        for (int j = 1; j <= metaData.getColumnCount(); j++) {
            columnIndexMap.put(metaData.getColumnName(j).toLowerCase(), j);
        }
        
        // 设置参数，只设置共同列的值
        for (int i = 0; i < commonColumns.size(); i++) {
            String columnName = commonColumns.get(i);
            
            // 在结果集中查找该列的索引
            Integer columnIndex = columnIndexMap.get(columnName.toLowerCase());
            if (columnIndex == null) {
                throw new SQLException("在结果集中找不到列: " + columnName);
            }
            
            // 设置参数值
            Object value = rs.getObject(columnIndex);
            insertStmt.setObject(i + 1, value); // PreparedStatement参数索引从1开始
            
            log.trace("设置参数 {}: 列名={}, 值={}", i + 1, columnName, value);
        }
    }
    
    /**
     * 设置插入语句的参数（不包含自增主键）
     */
    private void setInsertStatementParametersWithoutAutoIncrement(ResultSet rs, PreparedStatement insertStmt, 
                                                                 String tableName, String primaryKeyColumn) throws SQLException {
        // 获取共同列列表（已排除主键）
        List<String> commonColumns = commonColumnsThreadLocal.get();
        if (commonColumns == null || commonColumns.isEmpty()) {
            throw new SQLException("共同列列表为空");
        }
        
        // 获取结果集的元数据
        ResultSetMetaData metaData = rs.getMetaData();
        
        // 创建列名到索引的映射，提高查找效率
        Map<String, Integer> columnIndexMap = new HashMap<>();
        for (int j = 1; j <= metaData.getColumnCount(); j++) {
            columnIndexMap.put(metaData.getColumnName(j).toLowerCase(), j);
        }
        
        // 设置参数，只设置共同列的值（排除主键）
        for (int i = 0; i < commonColumns.size(); i++) {
            String columnName = commonColumns.get(i);
            
            // 在结果集中查找该列的索引
            Integer columnIndex = columnIndexMap.get(columnName.toLowerCase());
            if (columnIndex == null) {
                throw new SQLException("在结果集中找不到列: " + columnName);
            }
            
            // 设置参数值
            Object value = rs.getObject(columnIndex);
            insertStmt.setObject(i + 1, value); // PreparedStatement参数索引从1开始
            
            log.trace("设置参数 {}: 列名={}, 值={}", i + 1, columnName, value);
        }
    }
    
    /**
     * 设置插入语句的参数（包含主键，确保ID一致）
     */
    private void setInsertStatementParametersWithPrimaryKey(ResultSet rs, PreparedStatement insertStmt, 
                                                           String tableName, String primaryKeyColumn) throws SQLException {
        // 获取共同列列表（包含主键）
        List<String> commonColumns = commonColumnsThreadLocal.get();
        if (commonColumns == null || commonColumns.isEmpty()) {
            throw new SQLException("共同列列表为空");
        }
        
        // 获取结果集的元数据
        ResultSetMetaData metaData = rs.getMetaData();
        
        // 创建列名到索引的映射，提高查找效率
        Map<String, Integer> columnIndexMap = new HashMap<>();
        for (int j = 1; j <= metaData.getColumnCount(); j++) {
            columnIndexMap.put(metaData.getColumnName(j).toLowerCase(), j);
        }
        
        // 设置参数，包括主键列的值
        for (int i = 0; i < commonColumns.size(); i++) {
            String columnName = commonColumns.get(i);
            
            // 在结果集中查找该列的索引
            Integer columnIndex = columnIndexMap.get(columnName.toLowerCase());
            if (columnIndex == null) {
                throw new SQLException("在结果集中找不到列: " + columnName);
            }
            
            // 设置参数值
            Object value = rs.getObject(columnIndex);
            insertStmt.setObject(i + 1, value); // PreparedStatement参数索引从1开始
            
            // 对于主键列，记录日志以便调试
            if (columnName.equals(primaryKeyColumn)) {
                log.debug("设置主键参数 {}: 列名={}, 值={}", i + 1, columnName, value);
            } else {
                log.trace("设置参数 {}: 列名={}, 值={}", i + 1, columnName, value);
            }
        }
    }
    
    /**
     * 抽样验证数据一致性
     */
    private boolean validateSampleData(MigrationConfig config, String tableName) {
        // 实现抽样验证逻辑
        // 这里简化实现，实际应该随机抽取一定比例的数据进行比较
        return true;
    }
    
    /**
     * 保存进度到文件
     */
    private void saveProgressToFile(MigrationConfig config, MigrationProgress progress) {
        if (!config.isEnableResume()) {
            return;
        }
        
        try {
            File progressDir = new File(config.getProgressPath());
            if (!progressDir.exists()) {
                progressDir.mkdirs();
            }
            
            File progressFile = new File(progressDir, progress.getTableName() + ".progress");
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(progressFile))) {
                oos.writeObject(progress);
            }
        } catch (IOException e) {
            log.error("保存进度文件失败", e);
        }
    }
    
    /**
     * 从文件加载进度
     */
    private MigrationProgress loadProgressFromFile(MigrationConfig config, String tableName) {
        if (!config.isEnableResume()) {
            return null;
        }
        
        try {
            File progressFile = new File(config.getProgressPath(), tableName + ".progress");
            if (!progressFile.exists()) {
                return null;
            }
            
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(progressFile))) {
                return (MigrationProgress) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            log.error("加载进度文件失败", e);
            return null;
        }
    }
    
    /**
     * 保存报告到文件
     */
    private void saveReportToFile(MigrationConfig config, MigrationReport report) {
        try {
            File reportDir = new File(config.getReportPath());
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }
            
            String fileName = "migration_report_" + System.currentTimeMillis() + ".report";
            File reportFile = new File(reportDir, fileName);
            
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(reportFile))) {
                oos.writeObject(report);
            }
            
            log.info("迁移报告已保存到: {}", reportFile.getAbsolutePath());
        } catch (IOException e) {
            log.error("保存报告文件失败", e);
        }
    }
    
    @Override
    public List<String> getAvailableTables(MigrationConfig config) {
        List<String> tableNames = new ArrayList<>();
        
        try (Connection conn = getSourceConnection(config)) {
            DatabaseMetaData metaData = conn.getMetaData();
            
            // 获取所有表
            try (ResultSet rs = metaData.getTables(null, null, "%", new String[]{"TABLE"})) {
                while (rs.next()) {
                    String tableName = rs.getString("TABLE_NAME");
                    // 排除系统表
                    if (!tableName.startsWith("information_schema") && 
                        !tableName.startsWith("mysql") && 
                        !tableName.startsWith("performance_schema") && 
                        !tableName.startsWith("sys")) {
                        tableNames.add(tableName);
                    }
                }
            }
            
            log.info("获取到 {} 个可迁移的表", tableNames.size());
            return tableNames;
        } catch (SQLException e) {
            log.error("获取表列表失败", e);
            throw new RuntimeException("获取表列表失败", e);
        }
    }
}