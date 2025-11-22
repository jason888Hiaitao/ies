package com.example.wmsiescore.util;

import com.example.wmsiescore.common.ErrorCategory;
import com.example.wmsiescore.common.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 错误码工具类
 * 提供错误码相关的工具方法，包括分类、验证、统计等功能
 */
@Slf4j
@Component
public class ErrorCodeUtil {
    
    /**
     * 根据错误码获取错误分类
     */
    public static ErrorCategory getErrorCategory(int code) {
        int categoryCode = code / 1000 * 1000;
        return Arrays.stream(ErrorCategory.values())
                .filter(category -> category.getBaseCode() == categoryCode)
                .findFirst()
                .orElse(ErrorCategory.SYSTEM_ERROR);
    }
    
    /**
     * 验证错误码格式是否有效
     */
    public static boolean isValidErrorCode(int code) {
        return code >= 1000 && code <= 6999;
    }
    
    /**
     * 验证错误码是否属于指定分类
     */
    public static boolean isErrorCodeInCategory(int code, ErrorCategory category) {
        return category.contains(code);
    }
    
    /**
     * 获取指定分类下的所有错误码
     */
    public static List<ResponseCode> getErrorCodesByCategory(ErrorCategory category) {
        return Arrays.stream(ResponseCode.values())
                .filter(code -> category.contains(code.getCode()))
                .collect(Collectors.toList());
    }
    
    /**
     * 获取错误码统计信息
     */
    public static String getErrorCodeStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("错误码统计信息：\n");
        
        for (ErrorCategory category : ErrorCategory.values()) {
            List<ResponseCode> codes = getErrorCodesByCategory(category);
            stats.append(String.format("  %s (%s): %d个错误码\n", 
                category.getDescription(), category.getRange(), codes.size()));
            
            // 显示每个分类下的具体错误码
            for (ResponseCode code : codes) {
                stats.append(String.format("    - %d: %s\n", 
                    code.getCode(), code.getMessage()));
            }
        }
        
        return stats.toString();
    }
    
    /**
     * 根据错误码获取完整的错误信息
     */
    public static String getFullErrorMessage(int code) {
        try {
            ResponseCode responseCode = ResponseCode.fromCode(code);
            return String.format("错误码: %d, 错误信息: %s, 解决方案: %s", 
                responseCode.getCode(), responseCode.getMessage(), responseCode.getSuggestion());
        } catch (Exception e) {
            log.warn("无效的错误码: {}", code);
            return String.format("未知错误码: %d", code);
        }
    }
    
    /**
     * 获取错误码映射表（用于文档生成）
     */
    public static String getErrorCodeMappingTable() {
        StringBuilder table = new StringBuilder();
        table.append("| 错误码 | 错误类型 | 错误信息 | 解决方案 |\n");
        table.append("|-------|----------|----------|----------|\n");
        
        for (ResponseCode code : ResponseCode.values()) {
            ErrorCategory category = getErrorCategory(code.getCode());
            table.append(String.format("| %d | %s | %s | %s |\n", 
                code.getCode(), category.getDescription(), code.getMessage(), code.getSuggestion()));
        }
        
        return table.toString();
    }
    
    /**
     * 验证错误码是否已定义
     */
    public static boolean isErrorCodeDefined(int code) {
        return ResponseCode.isValidCode(code);
    }
    
    /**
     * 获取最接近的错误码（用于错误码映射）
     */
    public static ResponseCode getClosestErrorCode(int code) {
        if (ResponseCode.isValidCode(code)) {
            return ResponseCode.fromCode(code);
        }
        
        // 如果错误码未定义，返回对应分类的默认错误码
        ErrorCategory category = getErrorCategory(code);
        switch (category) {
            case BUSINESS_ERROR:
                return ResponseCode.USER_NOT_FOUND;
            case VALIDATION_ERROR:
                return ResponseCode.INVALID_PARAMETER;
            case DATA_ERROR:
                return ResponseCode.DATA_SAVE_FAILED;
            case FILE_ERROR:
                return ResponseCode.FILE_UPLOAD_FAILED;
            case SYSTEM_ERROR:
                return ResponseCode.INTERNAL_SERVER_ERROR;
            case NETWORK_ERROR:
                return ResponseCode.NETWORK_ERROR;
            default:
                return ResponseCode.INTERNAL_SERVER_ERROR;
        }
    }
    
    /**
     * 批量验证错误码
     */
    public static boolean validateErrorCodes(List<Integer> codes) {
        for (Integer code : codes) {
            if (!isValidErrorCode(code) || !isErrorCodeDefined(code)) {
                log.error("无效的错误码: {}", code);
                return false;
            }
        }
        return true;
    }
    
    /**
     * 生成错误码文档
     */
    public static String generateErrorCodeDocumentation() {
        StringBuilder doc = new StringBuilder();
        doc.append("# 错误码文档\n\n");
        
        for (ErrorCategory category : ErrorCategory.values()) {
            doc.append(String.format("## %s (%s)\n\n", 
                category.getDescription(), category.getRange()));
            
            List<ResponseCode> codes = getErrorCodesByCategory(category);
            for (ResponseCode code : codes) {
                doc.append(String.format("### %d - %s\n\n", 
                    code.getCode(), code.getMessage()));
                doc.append(String.format("- **描述**: %s\n", code.getMessage()));
                doc.append(String.format("- **解决方案**: %s\n\n", code.getSuggestion()));
            }
        }
        
        return doc.toString();
    }
}