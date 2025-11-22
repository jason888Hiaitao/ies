package com.example.wmsiescore.dto.query;

import lombok.Data;

/**
 * 知识点查询条件对象
 * 用于封装知识点表的查询条件
 */
@Data
public class EtKnowledgePointQuery {
    
    /**
     * 知识点ID
     */
    private Integer pointId;
    
    /**
     * 知识点名称
     */
    private String pointName;
    
    /**
     * 所属领域ID
     */
    private Integer fieldId;
    
    /**
     * 备注信息
     */
    private String memo;
    
    /**
     * 状态
     */
    private Integer state;
    
    /**
     * 偏移量（用于分页）
     */
    private Integer offset;
    
    /**
     * 每页大小（用于分页）
     */
    private Integer pageSize;
}