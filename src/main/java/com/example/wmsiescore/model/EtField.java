package com.example.wmsiescore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 题库领域表实体类
 */
@Schema(description = "题库领域表实体类")
@Data
public class EtField implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 领域ID，主键自增
     */
    @Schema(description = "领域ID，主键自增", example = "1")
    private Integer fieldId;
    
    /**
     * 领域名称
     */
    @Schema(description = "领域名称", example = "数学")
    private String fieldName;
    
    /**
     * 备注信息
     */
    @Schema(description = "备注信息", example = "数学相关领域")
    private String memo;
    
    /**
     * 状态：1-正常 0-废弃
     */
    @Schema(description = "状态：1-正常 0-废弃", example = "1")
    private BigDecimal state;
}
