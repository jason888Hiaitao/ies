package com.example.wmsiescore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 通用查询响应DTO
 * 用于统一返回查询结果
 */
@Data
@Schema(description = "通用查询响应DTO")
public class QueryResponse<T> {
    
    @Schema(description = "查询结果列表")
    private List<T> data;
    
    @Schema(description = "当前页码", example = "1")
    private Integer pageNum;
    
    @Schema(description = "每页大小", example = "10")
    private Integer pageSize;
    
    @Schema(description = "总记录数", example = "100")
    private Long total;
    
    @Schema(description = "总页数", example = "10")
    private Integer totalPages;
    
    @Schema(description = "是否有下一页", example = "true")
    private Boolean hasNext;
    
    @Schema(description = "是否有上一页", example = "false")
    private Boolean hasPrevious;
    
    @Schema(description = "查询类型", example = "BY_ID")
    private String queryType;
    
    @Schema(description = "实体类型", example = "EtUserGroup")
    private String entityType;
}