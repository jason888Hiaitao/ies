package com.example.wmsiescore.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 知识点保存DTO
 */
@Data
public class KnowledgeSaveDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 操作类型：create/update/delete/batch-delete
     */
    private String operation;
    
    /**
     * 知识点ID，更新时必填，创建时为空
     */
    private Long id;
    
    /**
     * 知识点名称
     */
    private String name;
    
    /**
     * 知识点描述
     */
    private String description;
    
    /**
     * 知识点状态
     */
    private String status;
    
    /**
     * 创建人
     */
    private String creator;
    
    /**
     * 有效部门
     */
    private String validdpt;
    
    /**
     * 有效来源
     */
    private String validsource;
    
    /**
     * 批量删除的ID列表
     */
    private List<Long> ids;
}