package com.example.wmsiescore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 通用CRUD请求DTO
 * 用于统一处理增删改操作
 */
@Data
@Schema(description = "通用CRUD请求DTO")
public class CrudRequest<T> {
    
    @Schema(description = "操作类型", example = "CREATE", allowableValues = {"CREATE", "UPDATE", "DELETE"})
    private String operationType;
    
    @Schema(description = "实体类型", example = "EtUserGroup", allowableValues = {"EtUserGroup", "EtQuestion", "EtExamPaper", "EtUser", "EtComment"})
    private String entityType;
    
    @Schema(description = "实体数据，用于CREATE和UPDATE操作")
    private T entityData;
    
    @Schema(description = "实体ID，用于DELETE和UPDATE操作", example = "1")
    private Long entityId;
    
    @Schema(description = "批量操作ID列表，用于批量DELETE操作")
    private java.util.List<Long> entityIds;
    
    @Schema(description = "额外参数，用于特殊操作")
    private java.util.Map<String, Object> extraParams;
}