package com.example.wmsiescore.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 知识点表实体类
 */
@ApiModel(value = "EtKnowledgePoint", description = "知识点表实体类")
@Data
public class EtKnowledgePoint implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 知识点ID，主键自增
     */
    @ApiModelProperty(value = "知识点ID，主键自增", example = "1")
    private Integer pointId;
    
    /**
     * 知识点名称
     */
    @ApiModelProperty(value = "知识点名称", example = "代数基础")
    private String pointName;
    
    /**
     * 所属领域ID，外键关联et_field.field_id
     */
    @ApiModelProperty(value = "所属领域ID，外键关联et_field.field_id", example = "1")
    private Integer fieldId;
    
    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息", example = "代数基础知识点")
    private String memo;
    
    /**
     * 状态：1-正常 0-废弃
     */
    @ApiModelProperty(value = "状态：1-正常 0-废弃", example = "1")
    private BigDecimal state;
    
    /**
     * 关联的领域信息（查询时使用）
     */
    @ApiModelProperty(value = "关联的领域信息（查询时使用）")
    private EtField field;
}