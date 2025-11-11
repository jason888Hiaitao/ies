package com.example.wmsiescore.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 题库保存DTO
 */
@Data
public class QuestionBankSaveDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 操作类型：create/update/delete/batch-delete
     */
    private String operation;
    
    /**
     * 题库ID，更新时必填，创建时为空
     */
    private Long id;
    
    /**
     * 题库名称
     */
    private String name;
    
    /**
     * 题库描述
     */
    private String description;
    
    /**
     * 题库状态
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