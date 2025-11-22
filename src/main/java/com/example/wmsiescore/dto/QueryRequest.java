package com.example.wmsiescore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 通用查询请求DTO
 * 用于统一处理各种查询操作
 */
@Data
@Schema(description = "通用查询请求DTO")
public class QueryRequest {
    
    @Schema(description = "查询类型", example = "BY_ID", allowableValues = {
        "BY_ID", "BY_GROUP_ID", "BY_TYPE", "BY_CREATOR", "BY_DIFFICULTY", 
        "BY_USER_ID", "BY_EXAM_PAPER_ID", "BY_QUESTION_ID", "SEARCH", "LIST_ALL", 
        "GET_VISIBLE", "GET_GROUP_MEMBERS", "GET_USER_GROUPS", "GET_COMPLETED_EXAMS", 
        "GET_PENDING_EXAMS", "GET_STATISTICS", "GET_ANALYSIS"
    })
    private String queryType;
    
    @Schema(description = "实体类型", example = "EtUserGroup", allowableValues = {
        "EtUserGroup", "EtQuestion", "EtExamPaper", "EtUser", "EtComment", 
        "EtUserExamHistory", "EtField", "EtKnowledgePoint"
    })
    private String entityType;
    
    @Schema(description = "查询参数，如ID、关键词等", example = "1")
    private Object queryParam;
    
    @Schema(description = "分页页码", example = "1")
    private Integer pageNum = 1;
    
    @Schema(description = "分页大小", example = "10")
    private Integer pageSize = 10;
    
    @Schema(description = "排序字段", example = "id")
    private String orderBy = "id";
    
    @Schema(description = "排序方向", example = "ASC", allowableValues = {"ASC", "DESC"})
    private String orderDirection = "ASC";
    
    @Schema(description = "额外查询参数")
    private java.util.Map<String, Object> extraParams;
}