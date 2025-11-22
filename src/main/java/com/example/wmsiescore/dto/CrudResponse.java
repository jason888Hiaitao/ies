package com.example.wmsiescore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 通用CRUD响应DTO
 * 用于统一返回CRUD操作结果
 */
@Data
@Schema(description = "通用CRUD响应DTO")
public class CrudResponse<T> {
    
    @Schema(description = "操作是否成功", example = "true")
    private Boolean success;
    
    @Schema(description = "操作结果数据，如创建的ID、更新的实体等")
    private T data;
    
    @Schema(description = "操作消息", example = "操作成功")
    private String message;
    
    @Schema(description = "批量操作成功数量", example = "5")
    private Integer successCount;
    
    @Schema(description = "批量操作失败数量", example = "0")
    private Integer failureCount;
    
    @Schema(description = "批量操作失败详情")
    private java.util.List<BatchOperationResult> failureDetails;
    
    /**
     * 批量操作结果
     */
    @Data
    @Schema(description = "批量操作结果")
    public static class BatchOperationResult {
        @Schema(description = "实体ID", example = "1")
        private Long entityId;
        
        @Schema(description = "操作是否成功", example = "true")
        private Boolean success;
        
        @Schema(description = "失败原因", example = "实体不存在")
        private String errorMessage;
    }
}