package com.example.wmsiescore.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 题库领域表实体类
 */
@ApiModel(value = "EtField", description = "题库领域表实体类")
@Data
public class EtField implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 领域ID，主键自增
     */
    @ApiModelProperty(value = "领域ID，主键自增", example = "1")
    private Integer fieldId;
    
    /**
     * 领域名称
     */
    @ApiModelProperty(value = "领域名称", example = "数学")
    private String fieldName;
    
    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息", example = "数学相关领域")
    private String memo;
    
    /**
     * 状态：1-正常 0-废弃
     */
    @ApiModelProperty(value = "状态：1-正常 0-废弃", example = "1")
    private BigDecimal state;
}