package com.example.wmsiescore.dto.query;

import lombok.Data;

/**
 * 试题查询条件对象
 * 用于封装试题表的查询条件
 */
@Data
public class EtQuestionQuery {
    
    /**
     * 试题ID
     */
    private Long id;
    
    /**
     * 试题名称
     */
    private String name;
    
    /**
     * 题目类型ID
     */
    private Long questionTypeId;
    
    /**
     * 答题时长
     */
    private Integer duration;
    
    /**
     * 分值
     */
    private Double points;
    
    /**
     * 所属分组ID
     */
    private Long groupId;
    
    /**
     * 是否可见
     */
    private Boolean isVisible;
    
    /**
     * 创建人
     */
    private String creator;
    
    /**
     * 曝光次数
     */
    private Integer exposeTimes;
    
    /**
     * 答对次数
     */
    private Integer rightTimes;
    
    /**
     * 答错次数
     */
    private Integer wrongTimes;
    
    /**
     * 难度
     */
    private String difficulty;
    
    /**
     * 偏移量（用于分页）
     */
    private Integer offset;
    
    /**
     * 每页大小（用于分页）
     */
    private Integer pageSize;
}