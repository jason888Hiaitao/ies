package com.example.wmsiescore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 知识点表实体类
 */
@Schema(description = "知识点表实体类")
@Data
public class EtKnowledgePoint implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 知识点ID，主键自增
     */
    @Schema(description = "知识点ID，主键自增", example = "1")
    private Integer pointId;
    
    /**
     * 知识点名称
     */
    @Schema(description = "知识点名称", example = "代数基础")
    private String pointName;
    
    /**
     * 所属领域ID，外键关联et_field.field_id
     */
    @Schema(description = "所属领域ID，外键关联et_field.field_id", example = "1")
    private Integer fieldId;
    
    /**
     * 备注信息
     */
    @Schema(description = "备注信息", example = "代数基础知识点")
    private String memo;
    
    /**
     * 状态：1-正常 0-废弃
     */
    @Schema(description = "状态：1-正常 0-废弃", example = "1")
    private BigDecimal state;
    
    /**
     * 关联的领域信息（查询时使用）
     */
    @Schema(description = "关联的领域信息（查询时使用）")
    private EtField field;
}
