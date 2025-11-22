package com.example.wmsiescore.dto.query;

import lombok.Data;

/**
 * 考试分析查询条件对象
 * 用于封装考试分析表的查询条件
 */
@Data
public class EtExamAnalysisQuery {
    
    /**
     * 考试分析ID
     */
    private Long id;
    
    /**
     * 试卷ID
     */
    private Long examPaperId;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 总分
     */
    private Double totalScore;
    
    /**
     * 用户得分
     */
    private Double userScore;
    
    /**
     * 及格状态
     */
    private String passStatus;
    
    /**
     * 考试时长
     */
    private Integer examDuration;
    
    /**
     * 提交时间
     */
    private String submitTime;
    
    /**
     * 偏移量（用于分页）
     */
    private Integer offset;
    
    /**
     * 每页大小（用于分页）
     */
    private Integer pageSize;
    
    /**
     * 开始日期（用于日期范围查询）
     */
    private String startDate;
    
    /**
     * 结束日期（用于日期范围查询）
     */
    private String endDate;
}