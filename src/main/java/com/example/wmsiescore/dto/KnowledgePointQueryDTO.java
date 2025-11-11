package com.example.wmsiescore.dto;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 知识点查询DTO
 */
@Data
public class KnowledgePointQueryDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
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
     * 状态：1-正常 0-废弃
     */
    private BigDecimal state;
    
    /**
     * 页码
     */
    private Integer pageNum = 1;
    
    /**
     * 每页大小
     */
    private Integer pageSize = 10;
}