package com.example.wmsiescore.dto;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 知识点保存DTO
 */
@Data
public class KnowledgePointSaveDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 操作类型：create-创建，update-更新，delete-删除，batch-delete-批量删除
     */
    private String operation;
    
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
     * 状态：1-正常 0-废弃
     */
    private BigDecimal state;
    
    /**
     * 批量删除的ID列表
     */
    private List<Integer> ids;
}