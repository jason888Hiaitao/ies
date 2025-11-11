package com.example.wmsiescore.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 知识点表实体类
 */
@ApiModel(value = "EtKnowledge", description = "知识点表实体类")
@Data
public class EtKnowledge implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 知识点ID
     */
    @ApiModelProperty(value = "知识点ID", example = "1")
    private Long id;
    
    /**
     * 知识点名称
     */
    @ApiModelProperty(value = "知识点名称", example = "代数基础")
    private String name;
    
    /**
     * 知识点描述
     */
    @ApiModelProperty(value = "知识点描述", example = "代数基础相关知识点")
    private String description;
    
    /**
     * 知识点状态
     */
    @ApiModelProperty(value = "知识点状态", example = "active")
    private String status;
    
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", example = "admin")
    private String creator;
    
    /**
     * 有效部门
     */
    @ApiModelProperty(value = "有效部门", example = "数学系")
    private String validdpt;
    
    /**
     * 有效来源
     */
    @ApiModelProperty(value = "有效来源", example = "教务处")
    private String validsource;
    
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2023-01-01 10:00:00")
    private Timestamp createTime;
    
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", example = "2023-01-01 10:00:00")
    private Timestamp updateTime;
}