package com.example.wmsiescore.common;

import lombok.Getter;

/**
 * 错误分类枚举
 * 定义系统错误码的层次化分类体系
 */
@Getter
public enum ErrorCategory {
    
    /**
     * 业务错误 (1000-1999)
     */
    BUSINESS_ERROR(1000, "业务错误"),
    
    /**
     * 参数验证错误 (2000-2999)
     */
    VALIDATION_ERROR(2000, "参数验证错误"),
    
    /**
     * 数据操作错误 (3000-3999)
     */
    DATA_ERROR(3000, "数据操作错误"),
    
    /**
     * 文件操作错误 (4000-4999)
     */
    FILE_ERROR(4000, "文件操作错误"),
    
    /**
     * 系统错误 (5000-5999)
     */
    SYSTEM_ERROR(5000, "系统错误"),
    
    /**
     * 网络错误 (6000-6999)
     */
    NETWORK_ERROR(6000, "网络错误");
    
    private final int baseCode;
    private final String description;
    
    ErrorCategory(int baseCode, String description) {
        this.baseCode = baseCode;
        this.description = description;
    }
    
    /**
     * 根据错误码获取对应的错误分类
     */
    public static ErrorCategory fromCode(int code) {
        int categoryCode = code / 1000 * 1000;
        for (ErrorCategory category : values()) {
            if (category.baseCode == categoryCode) {
                return category;
            }
        }
        return SYSTEM_ERROR; // 默认返回系统错误
    }
    
    /**
     * 验证错误码是否属于当前分类
     */
    public boolean contains(int code) {
        return code >= baseCode && code < baseCode + 1000;
    }
    
    /**
     * 获取分类下的错误码范围
     */
    public String getRange() {
        return baseCode + "-" + (baseCode + 999);
    }
}