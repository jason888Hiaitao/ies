package com.example.wmsiescore.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 知识点查询DTO
 */
@Data
public class KnowledgeQueryDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 页码
     */
    private Integer pageNum = 1;
    
    /**
     * 每页大小
     */
    private Integer pageSize = 10;
    
    /**
     * 知识点名称
     */
    private String name;
    
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
}