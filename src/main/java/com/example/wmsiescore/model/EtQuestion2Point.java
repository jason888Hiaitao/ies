package com.example.wmsiescore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

/**
 * 试题与知识点关联表实体类
 * 建立题目与知识点的多对多关系
 */
@Schema(description = "试题与知识点关联表实体类，建立题目与知识点的多对多关系")
@Data
public class EtQuestion2Point implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 关联ID，主键自增
     */
    @Schema(description = "关联ID，主键自增", example = "1")
    private Integer question2PointId;
    
    /**
     * 试题ID，关联试题表
     */
    @Schema(description = "试题ID，关联试题表", example = "1")
    private Integer questionId;
    
    /**
     * 知识点ID，外键关联et_knowledge_point.point_id
     */
    @Schema(description = "知识点ID，外键关联et_knowledge_point.point_id", example = "1")
    private Integer pointId;
}
