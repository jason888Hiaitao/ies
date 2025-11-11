package com.example.wmsiescore.dto;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 题库查询DTO
 */
@Data
public class FieldQueryDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 领域ID
     */
    private Integer fieldId;
    
    /**
     * 领域名称
     */
    private String fieldName;
    
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