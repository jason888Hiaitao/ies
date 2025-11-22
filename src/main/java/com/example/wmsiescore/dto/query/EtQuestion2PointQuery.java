package com.example.wmsiescore.dto.query;

import lombok.Data;

/**
 * 试题知识点关联查询条件对象
 * 用于封装试题知识点关联表的查询条件
 */
@Data
public class EtQuestion2PointQuery {
    
    /**
     * 关联ID
     */
    private Integer question2PointId;
    
    /**
     * 试题ID
     */
    private Integer questionId;
    
    /**
     * 知识点ID
     */
    private Integer pointId;
    
    /**
     * 偏移量（用于分页）
     */
    private Integer offset;
    
    /**
     * 每页大小（用于分页）
     */
    private Integer pageSize;
}