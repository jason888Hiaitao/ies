package com.example.wmsiescore.dto.query;

import lombok.Data;

/**
 * 用户考试历史查询条件对象
 * 用于封装用户考试历史表的查询条件
 */
@Data
public class EtUserExamHistoryQuery {
    
    /**
     * 历史记录ID
     */
    private Long histId;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 试卷ID
     */
    private Long examPaperId;
    
    /**
     * 实际用时
     */
    private Integer duration;
    
    /**
     * 获得分数
     */
    private Double pointGet;
    
    /**
     * 偏移量（用于分页）
     */
    private Integer offset;
    
    /**
     * 每页大小（用于分页）
     */
    private Integer pageSize;
}