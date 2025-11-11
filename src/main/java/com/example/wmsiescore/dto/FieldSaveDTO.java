package com.example.wmsiescore.dto;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 题库保存DTO
 */
@Data
public class FieldSaveDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 操作类型：create-创建，update-更新，delete-删除，batch-delete-批量删除
     */
    private String operation;
    
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
     * 状态：1-正常 0-废弃
     */
    private BigDecimal state;
    
    /**
     * 批量删除的ID列表
     */
    private List<Integer> ids;
}