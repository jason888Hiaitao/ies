package com.example.wmsiescore.migration;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据迁移工具类
 */
@Slf4j
public class MigrationUtil {
    
    /**
     * 数据类型映射表
     */
    private static final Map<String, String> TYPE_MAPPING = new HashMap<>();
    
    static {
        // MySQL到MySQL的类型映射（相同数据库，所以大部分类型相同）
        TYPE_MAPPING.put("TINYINT", "TINYINT");
        TYPE_MAPPING.put("SMALLINT", "SMALLINT");
        TYPE_MAPPING.put("MEDIUMINT", "MEDIUMINT");
        TYPE_MAPPING.put("INT", "INT");
        TYPE_MAPPING.put("INTEGER", "INTEGER");
        TYPE_MAPPING.put("BIGINT", "BIGINT");
        TYPE_MAPPING.put("FLOAT", "FLOAT");
        TYPE_MAPPING.put("DOUBLE", "DOUBLE");
        TYPE_MAPPING.put("DECIMAL", "DECIMAL");
        TYPE_MAPPING.put("NUMERIC", "NUMERIC");
        TYPE_MAPPING.put("DATE", "DATE");
        TYPE_MAPPING.put("TIME", "TIME");
        TYPE_MAPPING.put("DATETIME", "DATETIME");
        TYPE_MAPPING.put("TIMESTAMP", "TIMESTAMP");
        TYPE_MAPPING.put("YEAR", "YEAR");
        TYPE_MAPPING.put("CHAR", "CHAR");
        TYPE_MAPPING.put("VARCHAR", "VARCHAR");
        TYPE_MAPPING.put("BINARY", "BINARY");
        TYPE_MAPPING.put("VARBINARY", "VARBINARY");
        TYPE_MAPPING.put("TINYBLOB", "TINYBLOB");
        TYPE_MAPPING.put("BLOB", "BLOB");
        TYPE_MAPPING.put("MEDIUMBLOB", "MEDIUMBLOB");
        TYPE_MAPPING.put("LONGBLOB", "LONGBLOB");
        TYPE_MAPPING.put("TINYTEXT", "TINYTEXT");
        TYPE_MAPPING.put("TEXT", "TEXT");
        TYPE_MAPPING.put("MEDIUMTEXT", "MEDIUMTEXT");
        TYPE_MAPPING.put("LONGTEXT", "LONGTEXT");
        TYPE_MAPPING.put("ENUM", "ENUM");
        TYPE_MAPPING.put("SET", "SET");
        TYPE_MAPPING.put("BOOLEAN", "BOOLEAN");
        TYPE_MAPPING.put("BOOL", "BOOL");
        TYPE_MAPPING.put("BIT", "BIT");
        TYPE_MAPPING.put("JSON", "JSON");
    }
    
    /**
     * 获取目标数据库的数据类型
     * @param sourceType 源数据类型
     * @return 目标数据类型
     */
    public static String getTargetType(String sourceType) {
        return TYPE_MAPPING.getOrDefault(sourceType.toUpperCase(), sourceType);
    }
    
    /**
     * 比较两个表结构是否相同
     * @param sourceConn 源数据库连接
     * @param targetConn 目标数据库连接
     * @param tableName 表名
     * @return 是否相同
     */
    public static boolean compareTableStructure(Connection sourceConn, Connection targetConn, String tableName) {
        try {
            // 获取源表结构
            Map<String, ColumnInfo> sourceColumns = getTableColumns(sourceConn, tableName);
            
            // 获取目标表结构
            Map<String, ColumnInfo> targetColumns = getTableColumns(targetConn, tableName);
            
            // 比较列数
            if (sourceColumns.size() != targetColumns.size()) {
                log.info("表 {} 列数不同: 源表 {}, 目标表 {}", tableName, sourceColumns.size(), targetColumns.size());
                return false;
            }
            
            // 比较每一列
            for (Map.Entry<String, ColumnInfo> entry : sourceColumns.entrySet()) {
                String columnName = entry.getKey();
                ColumnInfo sourceColumn = entry.getValue();
                ColumnInfo targetColumn = targetColumns.get(columnName);
                
                if (targetColumn == null) {
                    log.info("目标表 {} 缺少列: {}", tableName, columnName);
                    return false;
                }
                
                // 比较数据类型
                String sourceType = getTargetType(sourceColumn.getDataType());
                String targetType = targetColumn.getDataType();
                
                if (!sourceType.equalsIgnoreCase(targetType)) {
                    log.info("表 {} 列 {} 数据类型不同: 源 {}, 目标 {}", tableName, columnName, sourceType, targetType);
                    return false;
                }
                
                // 比较是否可为空
                if (sourceColumn.isNullable() != targetColumn.isNullable()) {
                    log.info("表 {} 列 {} 可空属性不同: 源 {}, 目标 {}", tableName, columnName, sourceColumn.isNullable(), targetColumn.isNullable());
                    return false;
                }
            }
            
            return true;
        } catch (SQLException e) {
            log.error("比较表结构时发生错误", e);
            return false;
        }
    }
    
    /**
     * 获取表的列信息
     * @param conn 数据库连接
     * @param tableName 表名
     * @return 列信息映射
     */
    public static Map<String, ColumnInfo> getTableColumns(Connection conn, String tableName) throws SQLException {
        Map<String, ColumnInfo> columns = new HashMap<>();
        
        DatabaseMetaData metaData = conn.getMetaData();
        try (ResultSet rs = metaData.getColumns(null, null, tableName, null)) {
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                String dataType = rs.getString("TYPE_NAME");
                int columnSize = rs.getInt("COLUMN_SIZE");
                int nullable = rs.getInt("NULLABLE");
                String defaultValue = rs.getString("COLUMN_DEF");
                
                ColumnInfo columnInfo = new ColumnInfo();
                columnInfo.setName(columnName);
                columnInfo.setDataType(dataType);
                columnInfo.setSize(columnSize);
                columnInfo.setNullable(nullable == DatabaseMetaData.columnNullable);
                columnInfo.setDefaultValue(defaultValue);
                
                columns.put(columnName, columnInfo);
            }
        }
        
        return columns;
    }
    
    /**
     * 生成表结构差异报告
     * @param sourceConn 源数据库连接
     * @param targetConn 目标数据库连接
     * @param tableName 表名
     * @return 差异报告
     */
    public static String generateStructureDiffReport(Connection sourceConn, Connection targetConn, String tableName) {
        try {
            // 获取源表结构
            Map<String, ColumnInfo> sourceColumns = getTableColumns(sourceConn, tableName);
            
            // 获取目标表结构
            Map<String, ColumnInfo> targetColumns = getTableColumns(targetConn, tableName);
            
            StringBuilder report = new StringBuilder();
            report.append("表结构差异报告: ").append(tableName).append("\n");
            report.append("=====================================\n");
            
            // 检查源表有而目标表没有的列
            for (String columnName : sourceColumns.keySet()) {
                if (!targetColumns.containsKey(columnName)) {
                    report.append("目标表缺少列: ").append(columnName).append("\n");
                }
            }
            
            // 检查目标表有而源表没有的列
            for (String columnName : targetColumns.keySet()) {
                if (!sourceColumns.containsKey(columnName)) {
                    report.append("目标表多出列: ").append(columnName).append("\n");
                }
            }
            
            // 检查共同列的差异
            for (String columnName : sourceColumns.keySet()) {
                if (targetColumns.containsKey(columnName)) {
                    ColumnInfo sourceColumn = sourceColumns.get(columnName);
                    ColumnInfo targetColumn = targetColumns.get(columnName);
                    
                    String sourceType = getTargetType(sourceColumn.getDataType());
                    String targetType = targetColumn.getDataType();
                    
                    if (!sourceType.equalsIgnoreCase(targetType)) {
                        report.append("列 ").append(columnName).append(" 数据类型不同: 源 ").append(sourceType)
                              .append(", 目标 ").append(targetType).append("\n");
                    }
                    
                    if (sourceColumn.isNullable() != targetColumn.isNullable()) {
                        report.append("列 ").append(columnName).append(" 可空属性不同: 源 ").append(sourceColumn.isNullable())
                              .append(", 目标 ").append(targetColumn.isNullable()).append("\n");
                    }
                }
            }
            
            if (report.length() == ("表结构差异报告: " + tableName + "\n=====================================\n").length()) {
                report.append("表结构相同\n");
            }
            
            return report.toString();
        } catch (SQLException e) {
            log.error("生成表结构差异报告时发生错误", e);
            return "生成表结构差异报告时发生错误: " + e.getMessage();
        }
    }
    
    /**
     * 列信息内部类
     */
    public static class ColumnInfo {
        private String name;
        private String dataType;
        private int size;
        private boolean nullable;
        private String defaultValue;
        
        // Getters and Setters
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getDataType() {
            return dataType;
        }
        
        public void setDataType(String dataType) {
            this.dataType = dataType;
        }
        
        public int getSize() {
            return size;
        }
        
        public void setSize(int size) {
            this.size = size;
        }
        
        public boolean isNullable() {
            return nullable;
        }
        
        public void setNullable(boolean nullable) {
            this.nullable = nullable;
        }
        
        public String getDefaultValue() {
            return defaultValue;
        }
        
        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }
    }
}