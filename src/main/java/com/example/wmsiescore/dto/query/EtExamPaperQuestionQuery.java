package com.example.wmsiescore.dto.query;

import lombok.Data;

/**
 * 试卷试题关联查询条件对象
 * 用于封装试卷试题关联表的查询条件
 */
@Data
public class EtExamPaperQuestionQuery {
    
    /**
     * 关联ID
     */
    private Long id;
    
    /**
     * 试卷ID
     */
    private Long examPaperId;
    
    /**
     * 试题ID
     */
    private Long questionId;
    
    /**
     * 试题顺序
     */
    private Integer questionOrder;
    
    /**
     * 分值
     */
    private Double points;
    
    /**
     * 偏移量（用于分页）
     */
    private Integer offset;
    
    /**
     * 每页大小（用于分页）
     */
    private Integer pageSize;
}