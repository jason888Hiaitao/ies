package com.example.wmsiescore.dto.query;

import lombok.Data;

/**
 * 领域查询条件对象
 * 用于封装领域表的查询条件
 */
@Data
public class EtFieldQuery {
    
    /**
     * 领域ID
     */
    private Integer fieldId;
    
    /**
     * 领域名称
     */
    private String fieldName;
    
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